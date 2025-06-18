# MeshManager 백엔드 배포 가이드

## 개요
MeshManager 백엔드는 3개의 서비스로 구성되어 있습니다:
- `agent-service` (포트 8081)
- `cluster-service` (포트 8082)
- `cluster-management-service` (포트 8083)

## 배포 옵션

### 1. EC2 배포 (Docker Compose 사용)

#### 사전 준비
```bash
# Docker 및 Docker Compose 설치
sudo yum update -y
sudo yum install -y docker
sudo service docker start
sudo usermod -a -G docker ec2-user

# Docker Compose 설치
sudo curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose
```

#### 배포 실행
```bash
# 소스 코드 다운로드
git clone <repository-url>
cd MeshManagerBack

# 서비스 빌드 및 실행
docker-compose up -d --build

# 서비스 상태 확인
docker-compose ps
```

#### 포트 설정
EC2 보안 그룹에서 다음 포트들을 열어주세요:
- 8081 (agent-service)
- 8082 (cluster-service)
- 8083 (cluster-management-service)

### 2. EKS 배포 (Kubernetes 사용)

#### 사전 준비
```bash
# AWS CLI, kubectl, eksctl 설치
# EKS 클러스터 생성
eksctl create cluster --name mesh-manager-cluster --region ap-northeast-2 --nodegroup-name workers --node-type t3.medium --nodes 2
```

#### Docker 이미지 빌드 및 푸시
```bash
# ECR 로그인
aws ecr get-login-password --region ap-northeast-2 | docker login --username AWS --password-stdin <account-id>.dkr.ecr.ap-northeast-2.amazonaws.com

# 이미지 빌드 및 푸시
cd agent-service
docker build -t <account-id>.dkr.ecr.ap-northeast-2.amazonaws.com/mesh-agent-service:latest .
docker push <account-id>.dkr.ecr.ap-northeast-2.amazonaws.com/mesh-agent-service:latest

cd ../cluster-service
docker build -t <account-id>.dkr.ecr.ap-northeast-2.amazonaws.com/mesh-cluster-service:latest .
docker push <account-id>.dkr.ecr.ap-northeast-2.amazonaws.com/mesh-cluster-service:latest

cd ../cluster-management-service
docker build -t <account-id>.dkr.ecr.ap-northeast-2.amazonaws.com/mesh-cluster-management-service:latest .
docker push <account-id>.dkr.ecr.ap-northeast-2.amazonaws.com/mesh-cluster-management-service:latest
```

#### Kubernetes 배포
```bash
# 네임스페이스 생성
kubectl apply -f k8s/namespace.yaml

# Redis 배포
kubectl apply -f k8s/redis.yaml

# 서비스들 배포 (이미지 경로 수정 후)
kubectl apply -f k8s/services.yaml

# 서비스 상태 확인
kubectl get all -n mesh-manager

# LoadBalancer 외부 IP 확인
kubectl get svc -n mesh-manager
```

## 프론트엔드 연동 설정

### 1. 백엔드 URL 확인
배포 후 각 서비스의 외부 접근 가능한 URL을 확인하세요:

**EC2 배포의 경우:**
- `http://<EC2-PUBLIC-IP>:8081` (agent-service)
- `http://<EC2-PUBLIC-IP>:8082` (cluster-service)
- `http://<EC2-PUBLIC-IP>:8083` (cluster-management-service)

**EKS 배포의 경우:**
```bash
kubectl get svc -n mesh-manager
```
위 명령어로 각 서비스의 EXTERNAL-IP를 확인하세요.

### 2. 프론트엔드 환경변수 설정
`MeshManagerFront/next.config.ts` 파일에서 다음 값들을 실제 백엔드 URL로 변경하세요:

```typescript
env: {
  NEXT_PUBLIC_BACKEND_API_URL_CLUSTER: 'http://<BACKEND-URL>:8082',
  NEXT_PUBLIC_BACKEND_API_URL_AGENT: 'http://<BACKEND-URL>:8081', 
  NEXT_PUBLIC_BACKEND_API_URL_MANAGEMENT: 'http://<BACKEND-URL>:8083',
}
```

### 3. 프론트엔드 재배포
환경변수 변경 후 프론트엔드를 다시 빌드하고 S3에 업로드하세요:

```bash
cd MeshManagerFront
npm run build
# S3에 out 폴더 내용 업로드
```

## 트러블슈팅

### CORS 오류 발생 시
- 백엔드 서비스의 CORS 설정에 S3 도메인이 포함되어 있는지 확인
- 브라우저 개발자 도구에서 네트워크 탭 확인

### 서비스 연결 오류 시
- 보안 그룹/방화벽 포트 설정 확인
- 서비스 헬스체크 엔드포인트 테스트:
  - `http://<URL>:8081/actuator/health`
  - `http://<URL>:8082/actuator/health`
  - `http://<URL>:8083/actuator/health`

### Redis 연결 오류 시
- Redis 서비스가 정상 실행되고 있는지 확인
- 네트워크 연결 상태 확인 
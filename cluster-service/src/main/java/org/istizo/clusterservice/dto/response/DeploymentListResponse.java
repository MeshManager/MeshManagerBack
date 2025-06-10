package org.istizo.clusterservice.dto.response;

import org.istizo.clusterservice.dto.DeploymentInfo;

import java.util.List;

public record DeploymentListResponse(List<DeploymentInfo> data) {
}

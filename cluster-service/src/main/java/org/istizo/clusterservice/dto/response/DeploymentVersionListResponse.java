package org.istizo.clusterservice.dto.response;

import java.util.List;

public record DeploymentVersionListResponse(List<String> versions) {

    public static DeploymentVersionListResponse of(List<String> versions) {
        return new DeploymentVersionListResponse(versions);
    }
}

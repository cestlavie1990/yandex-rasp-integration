package com.minakov.yandexraspintegration.model;

import com.minakov.yandexraspintegration.model.embedded.CodeEmbedded;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "station")
public class StationEntity implements JpaEntity<String> {
    @Id
    @Column(updatable = false)
    private String id;

    @Embedded
    private CodeEmbedded code;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String direction;

    @Column(nullable = false)
    private String stationType;

    @Column(nullable = false)
    private String transportType;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;
}

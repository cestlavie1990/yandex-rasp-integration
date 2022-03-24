package com.minakov.yandexraspintegration.model;

import com.minakov.yandexraspintegration.model.embedded.CodeEmbedded;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "settlement")
public class SettlementEntity extends AbstractAuditEntity implements IEntity<UUID> {
    @Id
    @GeneratedValue
    @Column(updatable = false)
    private UUID id;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "region_id", nullable = false)
    private RegionEntity region;

    @Column(name = "region_id", nullable = false, insertable = false, updatable = false)
    private UUID regionId;

    @Embedded
    private CodeEmbedded code;

    @Column(nullable = false)
    private String title;

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "settlement", cascade = CascadeType.ALL)
    private List<StationEntity> stations = new ArrayList<>();

    public void setRegion(RegionEntity region) {
        this.region = region;
        this.regionId = region == null ? null : region.getId();
    }
}

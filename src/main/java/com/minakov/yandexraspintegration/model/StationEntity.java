package com.minakov.yandexraspintegration.model;

import com.minakov.yandexraspintegration.model.embedded.CodeEmbedded;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "station")
public class StationEntity extends AbstractAuditEntity implements IEntity<UUID> {
    @Id
    @GeneratedValue
    @Column(updatable = false)
    private UUID id;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "settlement_id", nullable = false)
    private SettlementEntity settlement;

    @Column(name = "settlement_id", nullable = false, insertable = false, updatable = false)
    private UUID settlementId;

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

    @Column
    private Double latitude;

    @Column
    private Double longitude;

    public void setSettlement(SettlementEntity settlement) {
        this.settlement = settlement;
        this.settlementId = settlement == null ? null : settlement.getId();
    }
}

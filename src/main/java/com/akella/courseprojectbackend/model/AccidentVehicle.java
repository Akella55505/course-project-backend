package com.akella.courseprojectbackend.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "accident_vehicle")
public class AccidentVehicle {

    @EmbeddedId
    private Id id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("accidentId")
    @JoinColumn(name = "accident_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_accident_vehicle_accident"))
    private Accident accident;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("personId")
    @JoinColumn(name = "vehicle_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_accident_vehicle_vehicle"))
    private Vehicle vehicle;

    @Embeddable
    public static class Id implements Serializable {
        private Long accidentId;

        private Long vehicleId;

        public Id() {}
        public Id(Long accidentId, Long vehicleId) {
            this.accidentId = accidentId;
            this.vehicleId = vehicleId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Id that)) return false;
            return Objects.equals(accidentId, that.accidentId)
                    && Objects.equals(vehicleId, that.vehicleId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(accidentId, vehicleId);
        }
    }
}

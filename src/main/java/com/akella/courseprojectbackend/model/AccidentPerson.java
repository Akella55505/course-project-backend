package com.akella.courseprojectbackend.model;

import com.akella.courseprojectbackend.enums.AccidentRole;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "accident_person")
public class AccidentPerson {

    @EmbeddedId
    private Id id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("accidentId")
    @JoinColumn(name = "accident_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_accident_person_accident"))
    private Accident accident;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("personId")
    @JoinColumn(name = "person_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_accident_person_person"))
    private Person person;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccidentRole role = AccidentRole.CULPRIT;

    @Embeddable
    public static class Id implements Serializable {
        @Column(name = "accident_id")
        private Long accidentId;

        @Column(name = "person_id")
        private Long vehicleId;

        public Id() {}
        public Id(Long accidentId, Long personId) {
            this.accidentId = accidentId;
            this.vehicleId = personId;
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

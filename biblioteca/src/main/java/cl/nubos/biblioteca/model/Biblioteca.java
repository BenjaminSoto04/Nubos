package cl.nubos.biblioteca.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "biblioteca")
public class Biblioteca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer usuarioId;

    @Column(nullable = false)
    private Integer videojuegoId;

    @Column(nullable = false)
    private LocalDate fechaAdquisicion;

    @Column(nullable = false)
    private Integer horasJugadas;

    @Column(nullable = true)
    private LocalDateTime ultimaSesion;
}

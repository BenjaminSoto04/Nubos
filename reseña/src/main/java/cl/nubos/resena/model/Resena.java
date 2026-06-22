package cl.nubos.resena.model;

import java.time.LocalDate;

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
@Table(name = "resena")
public class Resena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer usuarioId;

    @Column(nullable = false)
    private Integer videojuegoId;

    @Column(nullable = false)
    private Integer calificacion;

    @Column(nullable = false, length = 1000)
    private String comentario;

    @Column(nullable = false)
    private LocalDate fechaResena;

    @Column(nullable = false)
    private Boolean recomendado;
}

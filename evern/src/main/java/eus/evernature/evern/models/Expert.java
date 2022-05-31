package eus.evernature.evern.models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Entity(name = "experto")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Generated
public class Expert {

    @Id
    @Column(name = "experto_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "rol")
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles = new ArrayList<>();

    @OneToMany(mappedBy = "correctorExpert", cascade = CascadeType.ALL)
    List<Prediction> validatedPredictions = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "specialization_id")
    private Specialization specialization;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "nombre")
    private String name;

    @Column(name = "reset_password_token")
    private String resetPasswordToken;

    @Column(name = "activate_account_token")
    private String activateAccountToken;

    @Column(name = "cuenta_activada")
    private Boolean accountEnabled;

    @Column(name = "apellido")
    private String surname;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "nombre_empresa")
    private String companyName;

    @Column(name = "num_telefono")
    private String phoneNumber;

    @Column(name = "extension_telefono")
    private String phoneExtension;

    @CreationTimestamp
    @Column(name = "fecha_registro", updatable = false)
    private Timestamp registerDate;

    @UpdateTimestamp
    @Column(name = "fecha_modificacion")
    private Timestamp updatedTime;
}

package Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table
@Data
public class Users /*implements UserDetails*/ {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String usern_name;

    @Column(nullable = false)
    private String first_name;
    
    @Column(nullable = false)
    private String last_name;

    @Column(nullable = false)
    private String mail_direction;

    @Column(nullable = false)
    private String phone_number;

    //possibly add a historial of orders?
}

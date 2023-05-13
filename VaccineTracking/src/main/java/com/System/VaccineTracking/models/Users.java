package com.System.VaccineTracking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET deleted = true WHERE national_id=?")
@Where(clause = "deleted=false")
public class Users{

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "national_id")
    private String nationalId;

    @Column(name = "full_name")
    private String fullName;
    private String password;
    private int age;
    private String governorate;
    private String gender;
    private int doses;

    @Column(name = "waiting_list")
    private Boolean waitingList = false;

    @JsonIgnore
    private boolean deleted;
    @ManyToMany( fetch =  FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
    name = "user_roles" ,
        joinColumns ={ @JoinColumn(name = "user_id" , referencedColumnName = "user_id")} ,
        inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "role_id")})
    private List<Role> roles = new ArrayList<>();

}

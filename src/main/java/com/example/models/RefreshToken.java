package com.example.models;

import java.time.Instant;

import javax.persistence.*;

@Entity(name = "refreshtoken")
public class RefreshToken {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @OneToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  @Column(nullable = false, unique = true)
  private String token;

  @Column(nullable = false)
  private Instant expiryDate;

  public RefreshToken() {}

  public RefreshToken(User user, Instant expiryDate, String token) {
    this.user = user;
    this.expiryDate = expiryDate;
    this.token = token;
  }

  public void setId(Long id) {
    this.id = id;
  }
  public Long getId() {
    return id;
  }

  public User getUser() {
    return user;
  }

  public String getToken() {
    return token;
  }

  public Instant getExpiryDate() {
    return expiryDate;
  }

}

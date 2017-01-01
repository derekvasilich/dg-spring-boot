package com.example.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="quotes")
public class Quote {
 
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	public Long id;

    public String status;
    @Temporal(TemporalType.DATE)
    public Date expires;
    @Temporal(TemporalType.DATE)
    public Date accepted_on;

    @OneToOne
    @JoinColumn(name = "customer_id", insertable = false, updatable = false)
    public Customer customer;

    @OneToOne
    @JoinColumn(name = "contact_id", insertable = false, updatable = false)
    public Contact contact;

    public Float subtotal;
    public Float tax;
    public Float total;
    public Float original_amount;
    public Float balance;
}

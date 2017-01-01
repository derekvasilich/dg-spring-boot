package com.example.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.FetchType;

import com.example.converters.QuoteIdStringConverter;

@Entity
@Table(name="routes")
public class Route {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	public Long id;

	@OneToOne
	@JoinColumn(name = "company_id")
	public Company company;

    @Temporal(TemporalType.DATE)
    public Date date;

    // @OneToOne
    // @JoinColumn(name = "truck")
    public String truck;

    @Column(name="start_address")
    public Integer startAddress;

    @Basic(fetch=FetchType.LAZY)
    @Convert(converter = QuoteIdStringConverter.class)
    @Column(name="quote_ids")    
    public List<Quote> quotes;

    @OneToMany(mappedBy = "route")
    public List<RouteLocationVisit> visits;

    public Integer duration;
    public Integer distance;    

    public Boolean emailed;
    
}

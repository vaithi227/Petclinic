/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.owner;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Digits;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.core.style.ToStringCreator;
import org.springframework.samples.petclinic.model.ProgramsPerson;

/**
 * Simple JavaBean domain object representing an owner.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 */
@Entity
@Table(name = "programs")
public class Program extends ProgramsPerson {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "cluster")
    @NotEmpty
    private String cluster;

    @Column(name = "season")
    @NotEmpty
    private String season;
    
    @Column(name = "brand")
    @NotEmpty
    private String brand;

    @Column(name = "dept")
    @NotEmpty
    @Digits(fraction = 0, integer = 3)
    private String dept;

	public String getCluster() {
		return cluster;
	}

	public void setCluster(String cluster) {
		this.cluster = cluster;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}


    @Override
    public String toString() {
        return new ToStringCreator(this)


            .append("id", this.getId())
            .append("new", this.isNew())
            .append("programName", this.getProgramName())
            .append("buyer", this.getBuyer())
            .append("department", this.dept)
            .append("brand", this.brand)
            .append("cluster", this.cluster)
            .append("season",this.season)
            .toString();
    }
    
    

}

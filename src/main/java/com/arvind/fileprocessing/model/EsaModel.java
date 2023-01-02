package com.arvind.fileprocessing.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EsaModel {
	private String empId;
	private String firstName;
	private String lastname;
	private String emailId;
	private int totalHours;

}

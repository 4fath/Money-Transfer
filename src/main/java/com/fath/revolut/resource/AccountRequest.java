package com.fath.revolut.resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequest implements Serializable {

	private static final long serialVersionUID = 7717873803697439570L;

	private BigDecimal balance;

}

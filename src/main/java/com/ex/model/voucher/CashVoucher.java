package com.ex.model.voucher;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * 
 * @author Gabriel Ferrer
 * Can only have one type of expense per cash voucher
 *
 */

@Entity
@Getter @Setter @NoArgsConstructor
@Data public class CashVoucher extends Voucher {

}

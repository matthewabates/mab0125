package com.example.tool_rental.service;

import com.example.tool_rental.model.CheckoutRequest;
import com.example.tool_rental.model.RentalAgreement;
import com.example.tool_rental.model.Tool;
import com.example.tool_rental.model.ToolType;
import com.example.tool_rental.util.CostUtil;
import com.example.tool_rental.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
    Responsible for generating completed RentalAgreements from CheckoutRequests.
    Uses external dependencies to calculate charge days, cost, discount, final cost etc
 */
@Service
public class CheckoutService {

    @Autowired
    CostUtil costUtil;

    @Autowired
    DateUtil dateUtil;

    public RentalAgreement checkout(CheckoutRequest checkoutRequest) {
        Tool tool = new Tool(); //TODO look up from repo, throw exception that generates a good response
        ToolType toolType = tool.getToolType(); //TODO look up from repo, throw exception that generates a good response

        RentalAgreement rentalAgreement = new RentalAgreement();

        //from tool info
        rentalAgreement.setToolCode(tool.getToolCode());
        rentalAgreement.setToolType(toolType.getName());
        rentalAgreement.setToolBrand(tool.getBrand());
        rentalAgreement.setDailyRentalCharge(toolType.getDailyCharge());

        //from checkout request
        rentalAgreement.setRentalDays(checkoutRequest.getRentalDayCount());
        rentalAgreement.setCheckoutDate(checkoutRequest.getCheckoutDate());
        rentalAgreement.setDiscountPercent(checkoutRequest.getDiscountPercent());

        //calculated values
        rentalAgreement.setDueDate(dateUtil.calculateDueDate(rentalAgreement.getCheckoutDate(), rentalAgreement.getRentalDays()));
        rentalAgreement.setChargeDays(costUtil.calculateChargeDays(rentalAgreement, toolType));
        rentalAgreement.setPreDiscountCharge(costUtil.calculatePreDiscountCharge(rentalAgreement));
        rentalAgreement.setDiscountAmount(costUtil.calculateDiscountAmount(rentalAgreement));
        rentalAgreement.setFinalCharge(costUtil.calculateFinalCharge(rentalAgreement));

        return rentalAgreement;
    }
}

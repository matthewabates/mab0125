package com.example.tool_rental.service;

import com.example.tool_rental.model.CheckoutRequest;
import com.example.tool_rental.model.RentalAgreement;
import com.example.tool_rental.model.Tool;
import com.example.tool_rental.util.CostUtil;
import com.example.tool_rental.util.DateUtil;
import org.springframework.stereotype.Service;

/*
    Responsible for generating completed RentalAgreements from CheckoutRequests.
    Looks up tool info from database,
    Uses external Cost util to calculate charge days, cost, discount, final cost etc
 */
@Service
public class CheckoutService {

    private final ToolService toolService;
    private final CostUtil costUtil;
    private final DateUtil dateUtil;

    public CheckoutService(ToolService toolService, CostUtil costUtil, DateUtil dateUtil) {
        this.toolService = toolService;
        this.costUtil = costUtil;
        this.dateUtil = dateUtil;
    }

    public RentalAgreement checkout(CheckoutRequest checkoutRequest) {
        Tool tool = toolService.getTool(checkoutRequest.getToolCode());
        RentalAgreement rentalAgreement = new RentalAgreement();

        //from tool info
        rentalAgreement.setToolCode(tool.getToolCode());
        rentalAgreement.setToolType(tool.getToolType().getName());
        rentalAgreement.setToolBrand(tool.getBrand());
        rentalAgreement.setDailyRentalCharge(tool.getToolType().getDailyCharge());

        //from checkout request
        rentalAgreement.setRentalDays(checkoutRequest.getRentalDayCount());
        rentalAgreement.setCheckoutDate(checkoutRequest.getCheckoutDate());
        rentalAgreement.setDiscountPercent(checkoutRequest.getDiscountPercent());

        //calculated values
        rentalAgreement.setDueDate(dateUtil.calculateDueDate(rentalAgreement.getCheckoutDate(), rentalAgreement.getRentalDays()));
        rentalAgreement.setChargeDays(costUtil.calculateChargeDays(rentalAgreement, tool.getToolType()));
        rentalAgreement.setPreDiscountCharge(costUtil.calculatePreDiscountCharge(rentalAgreement));
        rentalAgreement.setDiscountAmount(costUtil.calculateDiscountAmount(rentalAgreement));
        rentalAgreement.setFinalCharge(costUtil.calculateFinalCharge(rentalAgreement));

        rentalAgreement.printToConsole();

        return rentalAgreement;
    }

}

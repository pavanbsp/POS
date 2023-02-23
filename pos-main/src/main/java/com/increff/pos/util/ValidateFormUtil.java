package com.increff.pos.util;

import com.increff.pos.exception.ApiException;
import com.increff.pos.model.form.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;

public class ValidateFormUtil {

    public static void validateBrandForm(BrandForm brandForm) throws ApiException {
        if (isNull(brandForm.getBrand())) {
            throw new ApiException("The brand field can't be null");
        }
        if (isNull(brandForm.getCategory())) {
            throw new ApiException("The category field can't be null");
        }
        if (brandForm.getBrand().trim().isEmpty()) {
            throw new ApiException("Please enter valid brand");
        }
        if (brandForm.getCategory().trim().isEmpty()) {
            throw new ApiException("Please enter valid category");
        }
        if(!isValidWord(brandForm.getBrand().trim())) {
            throw new ApiException("Brand field should not contain special characters");
        }
        if(!isValidWord(brandForm.getCategory().trim())) {
            throw new ApiException("Category field should not contain special characters");
        }
        if(brandForm.getBrand().trim().length() > 20) {
            throw new ApiException("Brand length should be less than 20");
        }
        if(brandForm.getCategory().trim().length() > 20) {
            throw new ApiException("Category length should be less than 20");
        }
    }

    public static void validateInventoryForm(InventoryForm inventoryForm) throws ApiException {
        if (isNull(inventoryForm.getBarcode())) {
            throw new ApiException("The barcode field can't be null");
        }
        if (isNull(inventoryForm.getQuantity())) {
            throw new ApiException("The quantity field can't be null");
        }
        if (inventoryForm.getBarcode().trim().isEmpty()) {
            throw new ApiException("Please enter valid barcode");
        }
        if (inventoryForm.getQuantity() < 0 ) {
            throw new ApiException("Quantity cannot be negative");
        }
        if(!isValidWord(inventoryForm.getBarcode().trim())) {
            throw new ApiException("Barcode field should not contain special characters");
        }
        if(inventoryForm.getBarcode().trim().length() > 20) {
            throw new ApiException("Barcode length should be less than 20");
        }
    }

    public static void validateProductForm(ProductForm productForm) throws ApiException {
        if (isNull(productForm.getBarcode())) {
            throw new ApiException("The barcode field can't be null");
        }
        if (isNull(productForm.getBrand())) {
            throw new ApiException("The brand field can't be null");
        }
        if (isNull(productForm.getCategory())) {
            throw new ApiException("The category field can't be null");
        }
        if (isNull(productForm.getName())) {
            throw new ApiException("The name field can't be null");
        }
        if (isNull(productForm.getMrp())) {
            throw new ApiException("The mrp field can't be null");
        }
        if (productForm.getBarcode().trim().isEmpty()) {
            throw new ApiException("Please enter valid barcode");
        }
        if (productForm.getName().trim().isEmpty()) {
            throw new ApiException("Please enter valid name");
        }
        if (productForm.getMrp() <= 0.0d) {
            throw new ApiException("MRP cannot be negative or zero");
        }
        if (productForm.getBrand().trim().isEmpty()) {
            throw new ApiException("Please enter valid brand");
        }
        if (productForm.getCategory().trim().isEmpty()) {
            throw new ApiException("Please enter valid category");
        }
        if(!isValidWord(productForm.getBrand().trim())) {
            throw new ApiException("Brand field should not contain special characters");
        }
        if(!isValidWord(productForm.getCategory().trim())) {
            throw new ApiException("Category field should not contain special characters");
        }
        if(!isValidWord(productForm.getBarcode().trim())) {
            throw new ApiException("Barcode field should not contain special characters");
        }
        if(!isValidWord(productForm.getName().trim())) {
            throw new ApiException("Name field should not contain special characters");
        }
        if(productForm.getBrand().trim().length() > 20) {
            throw new ApiException("Brand length should be less than 20");
        }
        if(productForm.getCategory().trim().length() > 20) {
            throw new ApiException("Category length should be less than 20");
        }
        if(productForm.getBarcode().trim().length() > 20) {
            throw new ApiException("Barcode length should be less than 20");
        }
        if(productForm.getName().trim().length() > 30) {
            throw new ApiException("Name length should be less than 30");
        }
    }

    public static void validateOrderItemsForm(OrderItemsForm orderItemsForm) throws ApiException {
        if (isNull(orderItemsForm.getBarcode())) {
            throw new ApiException("The barcode field can't be null");
        }
        if (isNull(orderItemsForm.getQuantity())) {
            throw new ApiException("The quantity field can't be null");
        }
        if (isNull(orderItemsForm.getSellingPrice())) {
            throw new ApiException("The selling price field can't be null");
        }
        if (orderItemsForm.getBarcode().trim().isEmpty()) {
            throw new ApiException("Please enter valid barcode");
        }
        if (orderItemsForm.getQuantity() <= 0) {
            throw new ApiException("Quantity cannot be negative or zero");
        }
        if (orderItemsForm.getSellingPrice() <= 0.0d) {
            throw new ApiException("Price cannot be negative or zero");
        }
        if(!isValidWord(orderItemsForm.getBarcode().trim())) {
            throw new ApiException("Barcode field should not contain special characters");
        }
        if(orderItemsForm.getBarcode().trim().length() > 20) {
            throw new ApiException("Barcode length should be less than 20");
        }
    }

    public static void validateUserForm(UserForm userForm) throws ApiException {
        if (isNull(userForm.getEmail())) {
            throw new ApiException("The email field can't be null");
        }
        if (isNull(userForm.getPassword())) {
            throw new ApiException("The password field can't be null");
        }
        if (userForm.getEmail().trim().isEmpty() || !isValidEmail(userForm.getEmail().trim())) {
            throw new ApiException("Please enter valid email address");
        }
        if (userForm.getPassword().isEmpty()) {
            throw new ApiException("Please enter valid password");
        }
        if (userForm.getPassword().trim().length() < 7) {
            throw new ApiException("Password should contains at least 7 characters");
        }
    }

    public static void validateLoginForm(UserForm userForm) throws ApiException {
        if (isNull(userForm.getEmail())) {
            throw new ApiException("The email field can't be null");
        }
        if (isNull(userForm.getPassword())) {
            throw new ApiException("The password field can't be null");
        }
        if (userForm.getEmail().trim().isEmpty() || !isValidEmail(userForm.getEmail().trim())) {
            throw new ApiException("Please enter valid email address");
        }
        if (userForm.getPassword().isEmpty()) {
            throw new ApiException("Please enter valid password");
        }
    }

    public static void validateSalesForm(SalesReportForm salesReportForm) throws ApiException {
        if (isNull(salesReportForm.getBrand())) {
            throw new ApiException("The brand field can't be null");
        }
        if (isNull(salesReportForm.getCategory())) {
            throw new ApiException("The category field can't be null");
        }
        if (isNull(salesReportForm.getFrom())) {
            throw new ApiException("The from date field can't be null");
        }
        if (isNull(salesReportForm.getTo())) {
            throw new ApiException("The to date field can't be null");
        }
        if (ChronoUnit.DAYS.between(salesReportForm.getFrom(), salesReportForm.getTo()) < 0) {
            throw new ApiException("From date is greater than to date");
        }
    }

    public static void validateDailyReportForm(DailyReportForm dailyReportForm) throws ApiException {
        if (dailyReportForm.getFrom() == null) {
            throw new ApiException("Please enter valid from date");
        }
        if (dailyReportForm.getTo() == null) {
            throw new ApiException("Please enter valid to date");
        }
        if (!isValidFormat("yyyy-mm-dd", dailyReportForm.getFrom())) {
            throw new ApiException("From date format is wrong, format should be 'yyyy-mm-dd'");
        }
        if (!isValidFormat("yyyy-mm-dd", dailyReportForm.getTo())) {
            throw new ApiException("To date format is wrong, format should be 'yyyy-mm-dd'");
        }
        if (dailyReportForm.getFrom().compareTo(dailyReportForm.getTo()) > 0) {
            throw new ApiException("From date is greater than to date");
        }
    }

    public static void validateInventoryReportForm(BrandForm brandForm) throws ApiException {
        if (isNull(brandForm.getBrand())) {
            throw new ApiException("The brand field can't be null");
        }
        if (isNull(brandForm.getCategory())) {
            throw new ApiException("The category field can't be null");
        }
    }

    public static void validateBrandReportForm(BrandForm brandForm) throws ApiException {
        if (isNull(brandForm.getBrand())) {
            throw new ApiException("The brand field can't be null");
        }
        if (isNull(brandForm.getCategory())) {
            throw new ApiException("The category field can't be null");
        }
    }

    public static boolean isValidFormat(String format, String value) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(value);
            if (!value.equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return date != null;
    }

    public static Boolean isValidWord(String word){
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9\\s-\\s_]*$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(word);
        return matcher.matches();
    }

    public static boolean isValidEmail(String emailStr) {
        Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(emailStr);
        return matcher.matches();
    }
}

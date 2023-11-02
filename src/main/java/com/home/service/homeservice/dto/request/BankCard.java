package com.home.service.homeservice.dto.request;

public record BankCard(String creditCardNumber,String cvv2,int captchaId,String captcha) {
}

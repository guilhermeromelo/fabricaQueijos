package models;

import java.text.ParseException;

public class Client {

    private String cpf;
    private String clientName;
    private String phone;
    private String address;
    private String facebookURL;
    private String instagramURL;
    private String creditCard;

    public Client() {
        this.cpf = "N/C";
        this.clientName = "N/C";
        this.phone = "N/C";
        this.address = "N/C";
        this.facebookURL = "N/C";
        this.instagramURL = "N/C";
        this.creditCard = "N/C";
    }

    public Client(String CPF, String name, String phone, String address, String facebookURL, String instagramURL, String creditCard) {
        this.cpf = CPF;
        this.clientName = name;
        this.phone = phone;
        this.address = address;
        this.facebookURL = facebookURL;
        this.instagramURL = instagramURL;
        this.creditCard = creditCard;
    }

    public String getCPF() {
        return cpf;
    }

    public void setCPF(String CPF) {
        this.cpf = CPF;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String name) {
        this.clientName = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFacebookURL() {
        return facebookURL;
    }

    public void setFacebookURL(String facebookURL) {
        this.facebookURL = facebookURL;
    }

    public String getInstagramURL() {
        return instagramURL;
    }

    public void setInstagramURL(String instagramURL) {
        this.instagramURL = instagramURL;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    @Override
    public String toString() {
        return "Client{" + "CPF=" + cpf + ", name=" + clientName + ", phone=" + phone + ", address=" + address + ", facebookURL=" + facebookURL + ", instagramURL=" + instagramURL + ", creditCard=" + creditCard + '}';
    }

    //VALIDATIONS
    public static boolean phoneValidation(String phone) {
        boolean valid = true;
        //Length Validation
        if (phone.length() != 15) {
            valid = false;
        } else {
            //DDD Validation
            //First Number of DDD
            if (phone.charAt(1) > 57 || phone.charAt(1) < 49) {
                valid = false;
            }
            //Second Number of DDD
            if (phone.charAt(2) > 57 || phone.charAt(2) < 48) {
                valid = false;
            }

            //Number Validation
            //1st, 2nd, 3rd, 4th, 5th position of Phone Number
            if (phone.charAt(5) > 57 || phone.charAt(5) < 48) {
                valid = false;
            }
            if (phone.charAt(6) > 57 || phone.charAt(6) < 49) {
                valid = false;
            }
            for (int i = 7; i <= 9; i++) {
                if (phone.charAt(i) > 57 || phone.charAt(i) < 48) {
                    valid = false;
                }
            }
            //6th, 7th, 8th, 9th position of Phone Number
            for (int i = 11; i <= 14; i++) {
                if (phone.charAt(i) > 57 || phone.charAt(i) < 48) {
                    valid = false;
                }
            }
        }

        return valid;
    }

    public static boolean CPFValidation(String cpf) {
        boolean valid = true;
        //Length Validation
        if (cpf.length() != 14) {
            valid = false;
        } else {
            //CPF Validation
            //1st position of cpf
            if (cpf.charAt(0) > 57 || cpf.charAt(0) < 49) {
                valid = false;
            }
            //1st, 2nd, 3rd position of cpf
            for (int i = 1; i <= 2; i++) {
                if (cpf.charAt(i) > 57 || cpf.charAt(i) < 48) {
                    valid = false;
                }
            }
            //4th, 5th, 6th position of cpf
            for (int i = 4; i <= 6; i++) {
                if (cpf.charAt(i) > 57 || cpf.charAt(i) < 48) {
                    valid = false;
                }
            }
            //7th, 8th, 9th position of cpf
            for (int i = 8; i <= 10; i++) {
                if (cpf.charAt(i) > 57 || cpf.charAt(i) < 48) {
                    valid = false;
                }
            }
            //10th, 11th position of cpf
            for (int i = 12; i <= 13; i++) {
                if (cpf.charAt(i) > 57 || cpf.charAt(i) < 48) {
                    valid = false;
                }
            }
        }

        return valid;
    }

    //MASKS
    public static javax.swing.text.DefaultFormatterFactory PhoneMask() {

        try {
            return new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) #####-####"));
        } catch (ParseException e) {
            System.out.println("\n Erro Encontrado: " + e.toString());
            return null;
        }
    }

    public static javax.swing.text.DefaultFormatterFactory CPFMask() {

        try {
            return new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##"));
        } catch (ParseException e) {
            System.out.println("\n Erro Encontrado: " + e.toString());
            return null;
        }
    }
}

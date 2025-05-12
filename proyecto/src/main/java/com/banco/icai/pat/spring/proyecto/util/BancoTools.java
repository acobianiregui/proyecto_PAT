package com.banco.icai.pat.spring.proyecto.util;


import com.banco.icai.pat.spring.proyecto.model.Sucursal;

import java.math.BigInteger;

//NOTA: DC es digitos de control en general y DC_CCC de la cuenta nacional
//Consultar docuementacion para saber como se generan y validan los Iban
public final class BancoTools {
    private BancoTools() {}; //Asi no se instancia ni queriendo
    //Atributos del banco (para el IBAN)
    private final static String codigo_pais="ES";
    private final static String codigo_banco="4835";
    private final static String codigo_sucursal_madrid="0418";
    private final static String codigo_sucursal_barcelona="1628";
    private final static String codigo_sucursal_bilbao="9362";



    public static String generarIban(Sucursal sucursal,String num_cuenta){
        StringBuilder num_builder= new StringBuilder(num_cuenta);
        String codigo_sucursal= devolverCodigoSucursal(sucursal);
        String dcc= calcularDC_CCC(codigo_banco,codigo_sucursal,num_cuenta);
        String ibanv1= codigo_banco + codigo_sucursal + dcc + num_cuenta + "142800";
        String dc= calcularDC(ibanv1);

        return codigo_pais+dc+" "+codigo_banco+" "+codigo_sucursal+" "+dcc+num_builder.substring(0,2)
                +" "+num_builder.substring(2,6)+" "+num_builder.substring(6,10);
    }


    public static String calcularDC_CCC(String entidad, String oficina, String numeroCuenta) {
        int dc1 = calcularDCParte(entidad + oficina);
        int dc2 = calcularDCParte(numeroCuenta);
        return String.valueOf(dc1) + String.valueOf(dc2);
    }

    private static int calcularDCParte(String parte) {
        int[] pesos = {1, 2, 4, 8, 5, 10, 9, 7, 3, 6};
        int suma = 0;
        int offset = pesos.length - parte.length(); // para parte de 8 o 10 dígitos

        for (int i = 0; i < parte.length(); i++) {
            int digito = Character.getNumericValue(parte.charAt(i));
            suma += digito * pesos[i + offset];
        }

        int resto = suma % 11;
        int resultado = 11 - resto;

        if (resultado == 11) return 0;
        if (resultado == 10) return 1;
        return resultado;
    }


    public static String calcularDC(String ibanSinDC) {
        String ibanNumerico = convertirLetrasANumeros(ibanSinDC);  // Convertir letras a números
        BigInteger numero = new BigInteger(ibanNumerico);
        int resto = numero.mod(BigInteger.valueOf(97)).intValue();
        int dc = 98 - resto;  // El dígito de control es 98 - resto
        return String.format("%02d", dc);  // Asegurarse de que el dígito de control tenga dos dígitos
    }


    // Convertir letras del IBAN en números (A=10, B=11, ..., Z=35)
    public static String convertirLetrasANumeros(String numero) {
        StringBuilder cadena = new StringBuilder();
        for (char c : numero.toCharArray()) {
            if (Character.isLetter(c)) {
                cadena.append(String.format("%02d", c - 'A' + 10));  // A -> 10, B -> 11, ..., Z -> 35
            } else {
                cadena.append(c);
            }
        }
        return cadena.toString();
    }

    // Devuelve el código de sucursal correspondiente
    public static String devolverCodigoSucursal(Sucursal sucursal) {
        switch (sucursal) {
            case MADRID: return codigo_sucursal_madrid;
            case BARCELONA: return codigo_sucursal_barcelona;
            case BILBAO: return codigo_sucursal_bilbao;
            default: return null;
        }
    }


    public static boolean validarIban(String iban) {
        //Quitar espacios
        iban = iban.replace(" ", "");

        //Longitud
        if (iban.length() != 24) {
            return false;
        }

        //Reorgnizar
        iban = iban.substring(4) + iban.substring(0, 4);

        StringBuilder ibanNumerico = new StringBuilder();
        for (int i = 0; i < iban.length(); i++) {
            char c = iban.charAt(i);
            if (Character.isDigit(c)) {
                ibanNumerico.append(c);
            } else {
                // Convertir letras a números (A=10, B=11, ..., Z=35)
                ibanNumerico.append((int) c - 55);
            }
        }

        // Verificar el IBAN con el algoritmo de módulo 97
        String ibanNumericoStr = ibanNumerico.toString();
        return modulo97(ibanNumericoStr);
    }

    private static boolean modulo97(String ibanNumerico) {
        String temp = ibanNumerico;
        while (temp.length() > 9) {
            String subStr = temp.substring(0, 9);
            long num = Long.parseLong(subStr);
            long mod = num % 97;
            temp = mod + temp.substring(9);
        }
        return Long.parseLong(temp) % 97 == 1;
    }
}

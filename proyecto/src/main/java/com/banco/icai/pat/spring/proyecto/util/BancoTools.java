package com.banco.icai.pat.spring.proyecto.util;


import com.banco.icai.pat.spring.proyecto.model.Sucursal;

import java.math.BigInteger;

//NOTA: DC es digitos de control en general y DC_CCC de la cuenta nacional
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

    //Proceso de calcular digitos de control, consultar documentacion para info
    public static String calcularDC(String ibanSinDC){
        String ibanNumerico = convertirLetrasANumeros(ibanSinDC);

        String ibanConcatenado= ibanNumerico+"00";
        BigInteger numero = new BigInteger(ibanConcatenado);

        int resto= numero.mod(BigInteger.valueOf(97)).intValue();

        int dc= 98 - (int)resto;
        return String.format("%02d",dc);
    }

    public static String convertirLetrasANumeros(String numero){
        //Mas facil con StringBuilder
        StringBuilder cadena = new StringBuilder();
        for(char c: numero.toCharArray()){
            if(Character.isLetter(c)){
                cadena.append(String.format("%02d",c- 'A'+10));
            }else{
                cadena.append(c);
            }
        }
        return cadena.toString(); //Devolvemos String
    }
    public static String devolverCodigoSucursal(Sucursal sucursal){
        if(sucursal.equals(Sucursal.MADRID)){
            return codigo_sucursal_madrid;
        }
        if(sucursal.equals(Sucursal.BARCELONA)){
            return codigo_sucursal_barcelona;
        }
        if(sucursal.equals(Sucursal.BILBAO)){
            return codigo_sucursal_bilbao;
        }
        return null;
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



}

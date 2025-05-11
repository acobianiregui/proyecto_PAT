package com.banco.icai.pat.spring.proyecto.util;


//NOTA: DC es digitos de control
public final class BancoTools {
    private BancoTools() {}; //Asi no se instancia ni queriendo
    //Atributos del banco (para el IBAN)
    private final String codigo_pais="ES";
    private final String codigo_banco="4835";
    private final String codigo_sucursal_madrid="0418";
    private final String codigo_sucursal_barcelona="1628";
    private final String codigo_sucursal_bilbao="9362";
    //private final String numero_cuenta=1L;


    public static String generarIban(){

        return null;
    }

    //Proceso de calcular digitos de control, consultar documentacion para info
    public static String calcularDC(String ibanSinDC){
        String ibanNumerico = convertirLetrasANumeros(ibanSinDC);

        String ibanConcatenado= ibanNumerico+"00";
        long resto= Long.parseLong(ibanConcatenado) % 97;

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

}

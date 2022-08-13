package br.ufg.inf.auditOP;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;

public class Bag {
    public Bag() throws IOException {
        extractJSONData();
        extractJSONDataOP();
    }

    private void extractJSONDataOP() throws IOException {
        File initialFile = new File("src/main/resources/data/audit/OP_Valor_202208121138.json");
        if (initialFile.exists()) {
            InputStream is = new FileInputStream("src/main/resources/data/audit/OP_Valor_202208121138.json");
            String jsonTxt = IOUtils.toString(is, "UTF-8");
            JSONObject json = new JSONObject(jsonTxt);
            JSONArray pagamentos = (JSONArray) json.get("Pagamentos");
            ArrayList<OrdemDePagamento> lista = new ArrayList<>();
            for (Object pagamento : pagamentos
            ) {
                Integer opID = (Integer) ((JSONObject) pagamento).get("opID");

                Integer numeroOP = (Integer) ((JSONObject) pagamento).get("nrOP");
                TipoOrdemPagamento tipo = TipoOrdemPagamento.valueOf(String.valueOf(((JSONObject) pagamento).get("tipoOP")));
                Double valor = (Double) ((JSONObject) pagamento).get("vlOP");
                OrdemDePagamento op = new OrdemDePagamento(opID, numeroOP, tipo, valor);
                lista.add(op);
            }
        }
    }

    public void extractJSONData() throws IOException {
        File initialFile = new File("src/main/resources/data/audit/Municipio_Porte_DimMunicipio_202208121846.json");
        if (initialFile.exists()) {
            InputStream is = new FileInputStream("src/main/resources/data/audit/Municipio_Porte_DimMunicipio_202208121846.json");
            String jsonTxt = IOUtils.toString(is, "UTF-8");
            JSONObject json = new JSONObject(jsonTxt);
            JSONArray municipios = (JSONArray) json.get("Municipios");
            ArrayList<Municipio> lista = new ArrayList<>();
            for (Object municipio : municipios
            ) {
                String porte = ((String) ((JSONObject) municipio).get("porte")).equals("Muito Pequeno") ? "Muito_Pequeno"
                        : (String) ((JSONObject) municipio).get("porte");

                Municipio municipioObject = new Municipio(new StringBuilder((String)
                        ((JSONObject) municipio).get("municipio")), Porte.valueOf(porte));
                lista.add(municipioObject);
            }
        }
    }
}
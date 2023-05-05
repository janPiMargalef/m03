/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.caixer;

/**
 *
 * @author JAN
 */
import com.mycompany.caixer.Compte.TipusCompte;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javafx.fxml.Initializable;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;


public class OperacionsController implements Initializable {
    
    private List<Bitllet> bitllets;

     @FXML
    private TextField quantitatTextField;
     
    @FXML
private ListView<Compte> comptesListView;
    
   @FXML
private ListView<Operacio> operacionsListView;
   @FXML
    private List<Operacio> operacions = new ArrayList<>();



      private String userEmail;
      private List<Compte> comptes = new ArrayList<>();

    public void initData(String email) {
        userEmail = email;
        cargarCuentasDeUsuario();
    }


    public void cargarCuentasDeUsuario() {
    String projectDirectory = System.getProperty("user.dir");
    File directory = new File(projectDirectory + File.separator + "data");
    File file = new File(directory, "comptes.csv");

    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = reader.readLine()) != null) {
            
            String[] parts = line.split(",");
            userEmail = "juan@example.com";
            if (parts.length >= 6 && parts[0].equals(userEmail) && parts[5].equalsIgnoreCase("false")) {
                long numeroCompte = Long.parseLong(parts[1]);
                String titularCompte = parts[2];
                double saldo = Double.parseDouble(parts[3]);
                Compte.TipusCompte tipusCompte = Compte.TipusCompte.valueOf(parts[4]);

                Compte compte = new Compte(numeroCompte, titularCompte, saldo, tipusCompte);
                comptes.add(compte);
                
            }
        }

        // Actualiza la ListView con las cuentas cargadas
        ObservableList<Compte> comptesObservableList = FXCollections.observableArrayList(comptes);
        comptesListView.setItems(comptesObservableList);
    } catch (IOException e) {
        System.err.println("Error al leer el archivo comptes.csv: " + e.getMessage());
    }
}

 
   @Override
public void initialize(URL location, ResourceBundle resources) {
    String projectDirectory = System.getProperty("user.dir");
    File directory = new File(projectDirectory + File.separator + "data");
    File bitlletsFile = new File(directory, "bitllets.csv");

    bitllets = Bitllet.carregarBitllets(bitlletsFile.getAbsolutePath());
    
    cargarCuentasDeUsuario();
    cargarOperacionesDeUsuario();
}
public void cargarOperacionesDeUsuario() {
    String projectDirectory = System.getProperty("user.dir");
    File directory = new File(projectDirectory + File.separator + "data");
    File file = new File(directory, "operacions.csv");

    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println("Leyendo línea: " + line);
            String[] parts = line.split(",");
            if (parts.length >= 4 && parts[1].equals(userEmail)) {
                String data = parts[0];
                String email = parts[1];
                String tipus = parts[2];
                double importe = Double.parseDouble(parts[3]);

                Operacio operacion = new Operacio(data, email, tipus, importe);
                operacions.add(operacion);
                System.out.println("Operación agregada: " + operacion); // Línea de depuración
            } else {
                System.out.println("Condición no cumplida: parts.length = " + parts.length + ", parts[1] = " + parts[1] + ", userEmail = " + userEmail); // Línea de depuración
            }
        }

       
        ObservableList<Operacio> operacionesObservableList = FXCollections.observableArrayList(operacions);
        operacionsListView.setItems(operacionesObservableList);
    } catch (IOException e) {
        System.err.println("Error al leer el archivo operacions.csv: " + e.getMessage());
    }
}






public void handleButtonPress(ActionEvent event) { //cargar teclat
    Button button = (Button) event.getSource();
    String buttonText = button.getText();
    quantitatTextField.setText(quantitatTextField.getText() + buttonText);
}

@FXML
Label missatge;

public void actualizarSaldoEnCSV(Compte compteActualizado) {
    String projectDirectory = System.getProperty("user.dir");
    File directory = new File(projectDirectory + File.separator + "data");
    File file = new File(directory, "comptes.csv");
    List<String> lines = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 6 && Long.parseLong(parts[1]) == compteActualizado.getNumeroCompte()) {
                lines.add(String.join(",", parts[0], String.valueOf(compteActualizado.getNumeroCompte()),
                        compteActualizado.getTitularCompte(), String.valueOf(compteActualizado.getSaldo()),
                        compteActualizado.getTipusCompte().toString(), parts[5]));
            } else {
                lines.add(line);
            }
        }
    } catch (IOException e) {
        System.err.println("Error al llegir larxiu comptes.csv: " + e.getMessage());
    }

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
        for (String line : lines) {
            writer.write(line);
            writer.newLine();
        }
    } catch (IOException e) {
        System.err.println("Error al escribir en el archivo comptes.csv: " + e.getMessage());
    }
}


public boolean retirarEfectivo(int importe, Compte compteSeleccionada) {
    Map<Integer, Integer> bitlletsARetirar = new HashMap<>();
    int importeRestante = importe;

    for (Bitllet bitllet : bitllets) {
        int denominacio = bitllet.getDenominacio();
        int quantitatDisponible = bitllet.getQuantitat();
        int bitlletsNecessaris = importeRestante / denominacio;

        if (bitlletsNecessaris > 0) {
            int bitlletsARetirarActuals = Math.min(quantitatDisponible, bitlletsNecessaris);
            bitlletsARetirar.put(denominacio, bitlletsARetirarActuals);
            importeRestante -= denominacio * bitlletsARetirarActuals;
        }

        if (importeRestante == 0) {
            break;
        }
    }

    if (importeRestante == 0) {
        // Actualiza la cantidad de billetes disponibles en el cajero
        for (Map.Entry<Integer, Integer> entry : bitlletsARetirar.entrySet()) {
            int denominacio = entry.getKey();
            int quantitatARetirar = entry.getValue();
            for (Bitllet bitllet : bitllets) {
                if (bitllet.getDenominacio() == denominacio) {
                    bitllet.setQuantitat(bitllet.getQuantitat() - quantitatARetirar);
                    break;
                }
            }
        }

        // Actualiza el saldo del compte seleccionada y guarda el cambio en el archivo comptes.csv
        compteSeleccionada.setSaldo(compteSeleccionada.getSaldo() - importe);
        actualizarSaldoEnCSV(compteSeleccionada);
        
        return true;
    } else {
        return false; // No es posible retirar el importe solicitado con los billetes disponibles
    }
}

    public void onRetirar(ActionEvent event) {
    Compte compteSeleccionada = comptesListView.getSelectionModel().getSelectedItem();

    if (compteSeleccionada != null) {
        int importe = Integer.parseInt(quantitatTextField.getText());

        if (importe % 5 != 0) {
            // Muestra un mensaje de error indicando que el monto no es válido
            System.out.println("El monto ingresado no es válido. Solo puedes retirar múltiplos de 5.");
        } else {
            boolean retiradaExitosa = retirarEfectivo(importe, compteSeleccionada);

            if (retiradaExitosa) {
                missatge.setText("Retirat");
                
            } else {
                // Muestra un mensaje de error indicando que no se pudo retirar el importe solicitado
                missatge.setText("Error al retirar");
                 registrarOperacion(userEmail, "Retirar", importe);
            }
        }
    } else {
        // Muestra un mensaje de error indicando que no se ha seleccionado ninguna cuenta
        missatge.setText("Selecciona un compte");
    }
}

public void depositarEfectivo(int importe, Compte compteSeleccionada) {
    // Suma la cantidad al saldo de la cuenta seleccionada
    compteSeleccionada.setSaldo(compteSeleccionada.getSaldo() + importe);

    // Actualiza el saldo de la cuenta seleccionada en el archivo comptes.csv
    actualizarSaldoEnCSV(compteSeleccionada);

    // Actualiza la cantidad de billetes disponibles en el cajero
    // Aquí, como ejemplo, se distribuye la cantidad depositada en billetes de 5
    for (Bitllet bitllet : bitllets) {
        if (bitllet.getDenominacio() == 5) {
            bitllet.setQuantitat(bitllet.getQuantitat() + (importe / 5));
            break;
        }
    }

    // Actualiza la información de los billetes en el archivo bitllets.csv
    actualizarBitlletsEnCSV();
}
public void onDepositar(ActionEvent event) {
    Compte compteSeleccionada = comptesListView.getSelectionModel().getSelectedItem();

    if (compteSeleccionada != null) {
        int importe = Integer.parseInt(quantitatTextField.getText());

        if (importe > 0) {
            depositarEfectivo(importe, compteSeleccionada);
            missatge.setText("Dinero depositado");
            registrarOperacion(userEmail, "Depositar", importe);
        } else {
            // Muestra un mensaje de error indicando que el importe no es válido
            missatge.setText("El importe ingresado no es válido. Debe ser mayor a 0.");
        }
    } else {
        // Muestra un mensaje de error indicando que no se ha seleccionado ninguna cuenta
        missatge.setText("Selecciona un compte");
    }
}
public void actualizarBitlletsEnCSV() {
    String projectDirectory = System.getProperty("user.dir");
    File directory = new File(projectDirectory + File.separator + "data");
    File file = new File(directory, "bitllets.csv");

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
        for (Bitllet bitllet : bitllets) {
            writer.write(bitllet.getDenominacio() + "," + bitllet.getQuantitat());
            writer.newLine();
        }
    } catch (IOException e) {
        System.err.println("Error al escribir en el archivo bitllets.csv: " + e.getMessage());
    }
}


@FXML
private TextField cuentaDestinoTextField;


@FXML
private Label mensajeTransferenciaLabel;


@FXML
private void realizarTransferencia() {
    Compte cuentaOrigen = comptesListView.getSelectionModel().getSelectedItem();
    long numeroCuentaOrigen = cuentaOrigen.getNumeroCompte();
    long numeroCuentaDestino;
    double importe;

    try {
        numeroCuentaDestino = Long.parseLong(cuentaDestinoTextField.getText());
        importe = Double.parseDouble(quantitatTextField.getText());
    } catch (NumberFormatException e) {
        mensajeTransferenciaLabel.setText("El número de cuenta destino o el importe no son válidos.");
        return;
    }

    if (cuentaOrigen.getTipusCompte() != Compte.TipusCompte.CORRIENTE) {
        mensajeTransferenciaLabel.setText("Solo se pueden hacer transferencias desde cuentas corrientes.");
        return;
    }

    Compte cuentaDestino = buscarCuentaPorNumero(numeroCuentaDestino);
    
    if (cuentaDestino == null) {
        mensajeTransferenciaLabel.setText("La cuenta destino no existe.");
        return;
    }

    if (cuentaOrigen.getSaldo() >= importe) {
        cuentaOrigen.setSaldo(cuentaOrigen.getSaldo() - importe);
        cuentaDestino.setSaldo(cuentaDestino.getSaldo() + importe);
        actualizarSaldoEnCSV(cuentaOrigen);
        actualizarSaldoEnCSV(cuentaDestino);

        // Obtiene el correo electrónico de la cuenta destino
        String emailCuentaDestino = obtenerEmailCuenta(numeroCuentaDestino);

        // Registra la operación en el archivo para la cuenta origen
        registrarOperacion(userEmail, "Transferencia Saliente", importe);
        
        // Registra la operación en el archivo para la cuenta destino
        registrarOperacion(emailCuentaDestino, "Transferencia Entrante", importe);

        mensajeTransferenciaLabel.setText("Transferencia realizada con éxito.");
    } else {
        mensajeTransferenciaLabel.setText("Saldo insuficiente en la cuenta origen.");
    }
}

private String obtenerEmailCuenta(long numeroCuenta) {
    String projectDirectory = System.getProperty("user.dir");
    File directory = new File(projectDirectory + File.separator + "data");
    File file = new File(directory, "comptes.csv");

    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 6 && Long.parseLong(parts[1]) == numeroCuenta) {
                return parts[0];
            }
        }
    } catch (IOException e) {
        System.err.println("Error al leer el archivo comptes.csv: " + e.getMessage());
    }

    return null;
}

private Compte buscarCuentaPorNumero(long numeroCuenta) {
    String projectDirectory = System.getProperty("user.dir");
    File directory = new File(projectDirectory + File.separator + "data");
    File file = new File(directory, "comptes.csv");

    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 6 && Long.parseLong(parts[1]) == numeroCuenta) {
                long numeroCompte = Long.parseLong(parts[1]);
                String titularCompte = parts[2];
                double saldo = Double.parseDouble(parts[3]);
                Compte.TipusCompte tipusCompte = Compte.TipusCompte.valueOf(parts[4]);

                return new Compte(numeroCompte, titularCompte, saldo, tipusCompte);
            }
        }
    } catch (IOException e) {
        System.err.println("Error al leer el archivo comptes.csv: " + e.getMessage());
    }

    return null;
}

 private void registrarOperacion(String email, String tipoOperacion, double importe) {
    String projectDirectory = System.getProperty("user.dir");
    File directory = new File(projectDirectory + File.separator + "data");
    File file = new File(directory, "operacions.csv");

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        writer.write(timestamp + "," + email + "," + tipoOperacion + "," + importe);
        writer.newLine();
    } catch (IOException e) {
        System.err.println("Error al escribir en el archivo operacions.csv: " + e.getMessage());
    }
}


}

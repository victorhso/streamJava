import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) throws Exception {

        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

       System.out.print("Caminho do arquivo a ser lido: ");
        String path = sc.nextLine();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            List<Funcionario> list = new ArrayList<>();

            String line = br.readLine();
            while (line != null) {
                String[] vetor = line.split(",");
                list.add(new Funcionario(vetor[0], vetor[1], Double.parseDouble(vetor[2])));
                line = br.readLine();
            }

            // preço médio dos produtos
            double avg = list.stream().map(p -> p.getSalary()).reduce(0.0, (x, y) -> x + y) / list.size();
            System.out.println("Preço médio: " + String.format("%.2f", avg));

            System.out.print("Digite um salário: $");
            double sal = sc.nextDouble();

            List<String> email = list.stream()
                .filter(s -> s.getSalary() > sal)
                .map(s -> s.getEmail())
                .sorted()
                .collect(Collectors.toList());
            
            System.out.println("E-mail de pessoas cujo salário é superior a $" + String.format("%.2f", sal) + ":");
            email.forEach(System.out::println);

            double sum = list.stream()
                .filter(s -> s.getName().charAt(0) == 'M')
                .map(x -> x.getSalary())
				.reduce(0.0, (x, y) -> x + y);
                //.mapToDouble(f -> f.getSalary()).sum();
            
                System.out.println("Soma dos salários dos funcionários que começam com 'M': $" + String.format("%.2f", sum));
        }

        catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        sc.close();
    }
}

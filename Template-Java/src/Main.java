import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class Main {

	public static void main(String[] args) {
		// LE O ARQUIVO
		String filePath = JOptionPane.showInputDialog("Informe o caminho completo do arquivo de entrada do labirinto:");
	
		if (filePath == null || filePath.trim().equals("")) {
			JOptionPane.showMessageDialog(null,
				    "Caminho do arquivo deve ser informado",
				    "Alerta",
				    JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		File f = new File(filePath);
		if (!f.exists() || f.isDirectory()) {
			JOptionPane.showMessageDialog(null,
				    "Caminho do arquivo informado é inválido",
				    "Alerta",
				    JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		List<String> lines = new ArrayList<String>();
		try {
			FileInputStream fstream = new FileInputStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

			String strLine;
			while ((strLine = br.readLine()) != null)
				lines.add(strLine);

			fstream.close();
		}catch (Exception e) {
			JOptionPane.showMessageDialog(null,
				    "Não foi possível ler o arquivo de entrada",
				    "Error",
				    JOptionPane.ERROR_MESSAGE);
			return;
        }
		
		String[] dimensoes = lines.get(0).split(" ");
		int linhas = Integer.parseInt(dimensoes[0]);
		int colunas = Integer.parseInt(dimensoes[1]);

		// Preenche matriz do labirinto
        String[][] matriz = new String[linhas][colunas];
        int lAtual = -1; // Posição inicial: linha
        int cAtual = -1; // Posição inicial: coluna

		int lPassada = -1; // Posição passada: linha
        int cPassada = -1; // Posição passada: coluna
        
		int lSaida = -1; // Saída: linha
        int cSaida = -1; // Saída: coluna

		String dAtual = "";//Direção atual

        // percorre toda a matriz (a partir da segunda linha do arquivo texto) para identificar a posição inicial e a saída
        for (int l = 1; l < lines.size(); l++) 
        {
            String[] line = lines.get(l).split(" ");
            for (int c = 0; c < line.length; c++)
            {
                String ll = line[c];
                matriz[l - 1][c] = ll;
			
                if (ll.equals("X"))
                {
                    // Posição inicial
                    lAtual = l - 1;
                    cAtual = c;
                }
                else if (ll.equals("0") && (l == 1 || c == 0 || l == lines.size() - 1 || c == line.length - 1))
                {
                    // Saída
                    lSaida = l - 1;
                    cSaida = c;
                }
            }
        }

        // Guarda o trajeto em uma list de string e já inicia com a posição de origem
        List<String> resultado = new ArrayList<String>();

		//Guardar posição da casa anterior
		List<String> cAnterior = new ArrayList<String>();
		List<String> lAnterior = new ArrayList<String>();
		List<String> letra = new ArrayList<String>();

		//Printar posição da primeira casa
		System.out.print("O ["+(lAtual+1)+", "+(cAtual+1)+"]"+"\n");
		resultado.add("O ["+(lAtual+1)+", "+(cAtual+1)+"]");

        // Percorre a matriz (labirinto) atá encontrar a saída, usando as regras de prioridade e posições não visitadas, e vai armazenando o trajeto na list resultado
		boolean achouSaida = lAtual == lSaida && cAtual == cSaida;
        while (!achouSaida)
        {
			if(Integer.parseInt(matriz[(lAtual-1)][cAtual]) == 0){//cima
				lPassada = lAtual;
				cPassada = cAtual;
				lAtual = lAtual-1;
				matriz[lPassada][cPassada] = "2";
				System.out.print("C ["+(lAtual+1)+", "+(cAtual+1)+"]"+"\n");
				resultado.add("C ["+(lAtual+1)+", "+(cAtual+1)+"]");
				cAnterior.add(""+cPassada+"");
				lAnterior.add(""+lPassada+"");
				letra.add("C");
			}else{
				try{
					if(Integer.parseInt(matriz[(lAtual)][cAtual-1]) == 0){//esquerda
						lPassada = lAtual;
						cPassada = cAtual;
						matriz[lPassada][cPassada] = "2";
						cAtual = cAtual-1;
						System.out.print("E ["+(lAtual+1)+", "+(cAtual+1)+"]"+"\n");
						resultado.add("E ["+(lAtual+1)+", "+(cAtual+1)+"]");
						cAnterior.add(""+cPassada+"");
						lAnterior.add(""+lPassada+"");
						letra.add("E");
					}else if(Integer.parseInt(matriz[(lAtual)][cAtual+1]) == 0){//direita
						lPassada = lAtual;
						cPassada = cAtual;
						matriz[lPassada][cPassada] = "2";
						cAtual = cAtual+1;
						System.out.print("D ["+(lAtual+1)+", "+(cAtual+1)+"]"+"\n");
						resultado.add("D ["+(lAtual+1)+", "+(cAtual+1)+"]");
						cAnterior.add(""+cPassada+"");
						lAnterior.add(""+lPassada+"");
						letra.add("D");
					}else if(Integer.parseInt(matriz[(lAtual+1)][cAtual]) == 0){//baixo
						lPassada = lAtual;
						cPassada = cAtual;
						matriz[lPassada][cPassada] = "2";
						lAtual = lAtual+1;
						System.out.print("B ["+(lAtual+1)+", "+(cAtual+1)+"]"+"\n");
						resultado.add("B ["+(lAtual+1)+", "+(cAtual+1)+"]");
						cAnterior.add(""+cPassada+"");
						lAnterior.add(""+lPassada+"");
						letra.add("B");
					}else if(Integer.parseInt(matriz[(lAtual-1)][cAtual]) == 0){//cima
						lPassada = lAtual;
						cPassada = cAtual;
						matriz[lPassada][cPassada] = "2";
						lAtual = lAtual-1;
						System.out.print("C ["+(lAtual+1)+", "+(cAtual+1)+"]"+"\n");
						resultado.add("C ["+(lAtual+1)+", "+(cAtual+1)+"]");
						cAnterior.add(""+cPassada+"");
						lAnterior.add(""+lPassada+"");
						letra.add("C");
					}else if(Integer.parseInt(matriz[(lAtual-1)][cAtual]) != 0 && Integer.parseInt(matriz[(lAtual)][cAtual-1]) != 0 && Integer.parseInt(matriz[(lAtual)][cAtual+1]) != 0 && Integer.parseInt(matriz[(lAtual+1)][cAtual]) != 0){
						if(Integer.parseInt(matriz[(lAtual-1)][cAtual]) == 2 && Integer.parseInt(matriz[(lAtual)][cAtual-1]) == 2 && Integer.parseInt(matriz[(lAtual)][cAtual+1]) == 2 && Integer.parseInt(matriz[(lAtual+1)][cAtual]) == 2){//Se tiver quatro colunas com dígito 2
							lPassada = lAtual;
							cPassada = cAtual;
							matriz[lPassada][cPassada] = "1";
							int indiceC = cAnterior.size();
							int indiceL = lAnterior.size();
							int indiceD = letra.size();
							lAtual = Integer.parseInt(lAnterior.get(indiceL-2));
							cAtual = Integer.parseInt(cAnterior.get(indiceC-2));
							dAtual = letra.get(indiceD-1);
							lAnterior.remove(indiceL-1);//<---
							cAnterior.remove(indiceC-1);
							lAnterior.remove(indiceL-1);
							cAnterior.remove(indiceC-1);
							letra.remove(indiceD-1);
							System.out.print(dAtual+" ["+(lAtual+1)+", "+(cAtual+1)+"]"+"\n");
							resultado.add(dAtual+" ["+(lAtual+1)+", "+(cAtual+1)+"]");
						}else if(Integer.parseInt(matriz[(lAtual-1)][cAtual]) == 2 && Integer.parseInt(matriz[(lAtual)][cAtual-1]) == 2 || Integer.parseInt(matriz[(lAtual-1)][cAtual]) == 2 && Integer.parseInt(matriz[(lAtual)][cAtual+1]) == 2){//Se tiver duas colunas com dígito 2
							lPassada = lAtual;
							cPassada = cAtual;
							matriz[lPassada][cPassada] = "1";
							int indiceC = cAnterior.size();
							int indiceL = lAnterior.size();
							int indiceD = letra.size();
							lAtual = Integer.parseInt(lAnterior.get(indiceL-1));
							cAtual = Integer.parseInt(cAnterior.get(indiceC-1));
							matriz[Integer.parseInt(lAnterior.get(indiceL-2))][Integer.parseInt(cAnterior.get(indiceC-2))]="3";
							dAtual = letra.get(indiceD-1);
							lAnterior.remove(indiceL-1);
							cAnterior.remove(indiceC-1);
							letra.remove(indiceD-1);
							System.out.print(dAtual+" ["+(lAtual+1)+", "+(cAtual+1)+"]"+"\n");
							resultado.add(dAtual+" ["+(lAtual+1)+", "+(cAtual+1)+"]");
						}else if(Integer.parseInt(matriz[(lAtual-1)][cAtual]) == 3){//cima
							lPassada = lAtual;
							cPassada = cAtual;
							matriz[lPassada][cPassada] = "2";
							lAtual = lAtual-1;
							System.out.print("C ["+(lAtual+1)+", "+(cAtual+1)+"]"+"\n");
							resultado.add("C ["+(lAtual+1)+", "+(cAtual+1)+"]");
							cAnterior.add(""+cPassada+"");
							lAnterior.add(""+lPassada+"");
							letra.add("C");
						}else if(Integer.parseInt(matriz[(lAtual)][cAtual-1]) == 3){//esquerda
							lPassada = lAtual;
							cPassada = cAtual;
							matriz[lPassada][cPassada] = "2";
							cAtual = cAtual-1;
							System.out.print("E ["+(lAtual+1)+", "+(cAtual+1)+"]"+"\n");
							resultado.add("E ["+(lAtual+1)+", "+(cAtual+1)+"]");
							cAnterior.add(""+cPassada+"");
							lAnterior.add(""+lPassada+"");
							letra.add("E");
						}else if(Integer.parseInt(matriz[(lAtual)][cAtual+1]) == 3){//direita
							lPassada = lAtual;
							cPassada = cAtual;
							matriz[lPassada][cPassada] = "2";
							cAtual = cAtual+1;
							System.out.print("D ["+(lAtual+1)+", "+(cAtual+1)+"]"+"\n");
							resultado.add("D ["+(lAtual+1)+", "+(cAtual+1)+"]");
							cAnterior.add(""+cPassada+"");
							lAnterior.add(""+lPassada+"");
							letra.add("D");
						}else if(Integer.parseInt(matriz[(lAtual+1)][cAtual]) == 3){//baixo
							lPassada = lAtual;
							cPassada = cAtual;
							matriz[lPassada][cPassada] = "2";
							lAtual = lAtual+1;
							System.out.print("B ["+(lAtual+1)+", "+(cAtual+1)+"]"+"\n");
							resultado.add("B ["+(lAtual+1)+", "+(cAtual+1)+"]");
							cAnterior.add(""+cPassada+"");
							lAnterior.add(""+lPassada+"");
							letra.add("B");
						}else if(Integer.parseInt(matriz[(lAtual-1)][cAtual]) == 2){//cima
							matriz[lAtual][cAtual] = "1";
							lAtual = lAtual-1;
							int indiceC = cAnterior.size();
							int indiceL = lAnterior.size();
							lAnterior.remove(indiceL-1);
							cAnterior.remove(indiceC-1);
							System.out.print("C ["+(lAtual+1)+", "+(cAtual+1)+"]"+"\n");
							resultado.add("C ["+(lAtual+1)+", "+(cAtual+1)+"]");
							letra.add("C");
						}else if(Integer.parseInt(matriz[(lAtual)][cAtual-1]) == 2){//esquerda
							matriz[lAtual][cAtual] = "1";
							cAtual = cAtual-1;
							int indiceC = cAnterior.size();
							int indiceL = lAnterior.size();
							lAnterior.remove(indiceL-1);
							cAnterior.remove(indiceC-1);
							System.out.print("E ["+(lAtual+1)+", "+(cAtual+1)+"]"+"\n");
							resultado.add("E ["+(lAtual+1)+", "+(cAtual+1)+"]");
							letra.add("E");
						}else if(Integer.parseInt(matriz[(lAtual)][cAtual+1]) == 2){//direita
							matriz[lAtual][cAtual] = "1";
							cAtual = cAtual+1;
							int indiceC = cAnterior.size();
							int indiceL = lAnterior.size();
							lAnterior.remove(indiceL-1);
							cAnterior.remove(indiceC-1);
							System.out.print("D ["+(lAtual+1)+", "+(cAtual+1)+"]"+"\n");
							resultado.add("D ["+(lAtual+1)+", "+(cAtual+1)+"]");
							letra.add("D");
						}else if(Integer.parseInt(matriz[(lAtual+1)][cAtual]) == 2){//baixo
							matriz[lAtual][cAtual] = "1";
							lAtual = lAtual+1;
							int indiceC = cAnterior.size();
							int indiceL = lAnterior.size();
							lAnterior.remove(indiceL-1);
							cAnterior.remove(indiceC-1);
							System.out.print("B ["+(lAtual+1)+", "+(cAtual+1)+"]"+"\n");
							resultado.add("B ["+(lAtual+1)+", "+(cAtual+1)+"]");
							letra.add("B");
						}
					}
				//Caso a casa esteja em uma extremidade					
				}catch(Exception e){
					if(Integer.parseInt(matriz[(lAtual)][cAtual+1]) != 1){//direita
						lPassada = lAtual;
						cPassada = cAtual;
						matriz[lPassada][cPassada] = "1";
						cAtual = cAtual+1;
						System.out.print("D ["+(lAtual+1)+", "+(cAtual+1)+"]"+"\n");
						resultado.add("D ["+(lAtual+1)+", "+(cAtual+1)+"]");
						cAnterior.add(""+cPassada+"");
						lAnterior.add(""+lPassada+"");
						letra.add("D");
					}else if(Integer.parseInt(matriz[(lAtual+1)][cAtual]) != 1){//baixo
						lPassada = lAtual;
						cPassada = cAtual;
						matriz[lPassada][cPassada] = "1";
						lAtual = lAtual+1;
						System.out.print("B ["+(lAtual+1)+", "+(cAtual+1)+"]"+"\n");
						resultado.add("B ["+(lAtual+1)+", "+(cAtual+1)+"]");
						cAnterior.add(""+cPassada+"");
						lAnterior.add(""+lPassada+"");
						letra.add("B");
					}else if(Integer.parseInt(matriz[(lAtual-1)][cAtual]) != 1){//cima
						lPassada = lAtual;
						cPassada = cAtual;
						matriz[lPassada][cPassada] = "1";
						lAtual = lAtual-1;
						System.out.print("C ["+(lAtual+1)+", "+(cAtual+1)+"]"+"\n");
						resultado.add("C ["+(lAtual+1)+", "+(cAtual+1)+"]");
						cAnterior.add(""+cPassada+"");
						lAnterior.add(""+lPassada+"");
						letra.add("C");
					}else if(Integer.parseInt(matriz[(lAtual)][cAtual-1]) != 1){//esquerda
						lPassada = lAtual;
						cPassada = cAtual;
						matriz[lPassada][cPassada] = "1";
						cAtual = cAtual-1;
						System.out.print("E ["+(lAtual+1)+", "+(cAtual+1)+"]"+"\n");
						resultado.add("E ["+(lAtual+1)+", "+(cAtual+1)+"]");
						cAnterior.add(""+cPassada+"");
						lAnterior.add(""+lPassada+"");
						letra.add("E");
					}
				}
			}

            // Achou a saída?
            achouSaida = lAtual == lSaida && cAtual == cSaida;
        }
		
		
		// Escreve no arquivo texto a saída
        String folderPath = f.getParent();
        String fileName = f.getName();
		String outputPath = folderPath + "\\saida-" + fileName;
		
		try {
			File fout = new File(outputPath);
			FileOutputStream fos = new FileOutputStream(fout);
		 
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		 
			for (int i = 0; i < resultado.size(); i++) {
				bw.write(resultado.get(i));
				bw.newLine();
			}
		 
			bw.close();
		}catch (Exception e) {
			JOptionPane.showMessageDialog(null,
				    "Não foi possível escreve o arquivo de saída",
				    "Error",
				    JOptionPane.ERROR_MESSAGE);
			return;
        }
	}

}

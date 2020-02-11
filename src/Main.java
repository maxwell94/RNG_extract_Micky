import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Scanner;

public class Main {

	
	public static int cont036 = 0; 
	public static int cont051 = 0; 
	public static int contDecks = 0; 
	public static int contFloat = 0; 
	public static int contRaw = 0; 
	public static String pth;
	public static int contatore = 1; 
	
	public static void main(String[] args) throws IOException {
		
		String pathInput = ""; 
		String pathOutput = "";
		String pathOutFloat = ""; 
		File mainDirectory = null ;
		File directoryFloat = null;
		
		Scanner scanner = new Scanner(System.in);
		
		/*Path da cui prendere i vari files */
		System.out.print("Inserisci path input : ");
		pathInput = scanner.nextLine() ; 
		
		/*Path in cui salvare la copia dei files */
		System.out.print("Inserisci path output: ");
		pathOutput = scanner.nextLine(); 
		pth = pathOutput; 
		
	
		/*Creo il folder OUT al path passato come parametro */
		System.out.print("Creazione folder OUT ... \n");
		creaFoldersOut(pathOutput);
		System.out.print("Fine");
		System.out.print("\nCopia files ... \n");
		/*Vado nel folder di input , guardo se i vari files sono delle cartelle , Se sono delle cartelle 
		 * chiamo la mia funzione listaFiles che mi darà automaticamente tutti i files che si trovano all'interno
		 * di questa cartella */
		mainDirectory = new File(pathInput);
		String [] nomiFiles = mainDirectory.list();
		
		for(String s: nomiFiles) {
			
			if(!s.endsWith(".zip")) { // Se non è uno zip
				
				if(!s.equals("OUT")) { // Se non è il folder OUT
					
					//Chiamo la mia funzione listaFiles che mi copierà tutti i files presenti dentro la cartella e le sue sottocartelle nel folder di destinazione
					copiaFiles(pathInput+"\\"+s, pathOutput);
					//System.out.print("\n"+pathInput+"\\"+s);
				}
			}
			
		}
		System.out.print("Fine");
		System.out.print("\nModifica file extraction_4_float_x_300000.txt");
		pathOutFloat = pathOutput+"\\OUT\\Float";
		directoryFloat = new File(pathOutFloat);
		String [] fileFloats = directoryFloat.list();
		for(String s : fileFloats) {
			//System.out.println(s);
			modificaDati(pathOutFloat,s);
		}
		System.out.print("\nFine");
		//listaFiles(pathInput);
		

	}
	
	
	/*Metodo che crea i folders di output al path che gli passi come parametro */
	public static void creaFoldersOut(String pathOutput) {
		
		File folderOut = new File(pathOutput+"\\OUT");
		File folder036 = new File(pathOutput+"\\OUT\\0-36");
		File folder051 = new File(pathOutput+"\\OUT\\0-51");
		File folderDecks = new File(pathOutput+"\\OUT\\Decks");
		File folderFloat = new File(pathOutput+"\\OUT\\Float");
		File folderRaw = new File(pathOutput+"\\OUT\\Raw");
		File folderProva = new File(pathOutput+"\\OUT\\Prova");
		
		if(!folderOut.exists()) {
			boolean creato = folderOut.mkdir();
			if(creato) {
				// C:\Users\Max Dedou\Desktop\prova\data
				/*Creo ora le 5 sottocartelle nel folder OUT */
				folder036.mkdir(); 
				folder051.mkdir(); 
				folderDecks.mkdir();
				folderFloat.mkdir();
				folderRaw.mkdir();
				folderProva.mkdir();
				
			}
		}
		else {
			System.out.println("\nFolder pricipale OUT non creato! perché esiste già ...");
		}
			 		
	}
	
	
	/*Metodo che copia un file da un folder a un altro */
	public static void copy(File sourceFile, File destFile) throws IOException {
	    
		if (!sourceFile.exists()) {
	        return;
	    }
	    if (!destFile.exists()) {
	        destFile.createNewFile();
	    }
	    FileChannel source = null;
	    FileChannel destination = null;
	    source = new FileInputStream(sourceFile).getChannel();
	    destination = new FileOutputStream(destFile).getChannel();
	    if (destination != null && source != null) {
	        destination.transferFrom(source, 0, source.size());
	    }
	    if (source != null) {
	        source.close();
	    }
	    if (destination != null) {
	        destination.close();
	    }

	}
	
	
	/*Metodo che legge i files dentro le cartelle e sottocartelle prendendo un path come parametro */
	public static void copiaFiles(String pathInput,String pathOutput) throws IOException {
		
		File folderIn = new File(pathInput);
		String folder036 = pathOutput+"\\OUT\\0-36";
		String folder051 = pathOutput+"\\OUT\\0-51";
		String folderDecks = pathOutput+"\\OUT\\Decks";
		String folderFloat = pathOutput+"\\OUT\\Float";
		String folderRaw = pathOutput+"\\OUT\\Raw";
		
		/*Prende tutti i files dentro un folder */
		File [] files = folderIn.listFiles();
		
		if(files != null) {
		    
			for(File f: files) {
				
				if(f.isFile() ) {
					
					System.out.println(f.getAbsolutePath());
					
					if( f.getAbsolutePath().contains("extraction_1_scaled_0-51_x_300000") ) {
						//f.getAbsolutePath sarà il mio file di input
						cont051++;
						File fDest = new File(folder051+"\\extraction_1_scaled_0-51_x_300000_"+cont051+".txt");
						copy(f,fDest);
					}
					else if( f.getAbsolutePath().contains("extraction_2_scaled_0-36_x_300000") ) {
						cont036 ++;
						File fDest = new File(folder036+"\\extraction_2_scaled_0-36_x_300000_"+cont036+".txt");
						copy(f,fDest);
					}
					else if( f.getAbsolutePath().contains("extraction_3_raw_32bit_x_3500000") ) {
						contRaw ++;
						File fDest = new File(folderRaw+"\\extraction_3_raw_32bit_x_3500000_"+contRaw+".bin");
						copy(f,fDest);
					}
					else if( f.getAbsolutePath().contains("extraction_4_float_x_300000") ) {
						contFloat ++;
						File fDest = new File(folderFloat+"\\extraction_4_float_x_300000_"+contFloat+".txt");
						copy(f,fDest);
					}
					else if( f.getAbsolutePath().contains("extraction_5_shuffled_deck") ) {
						contDecks ++;
						File fDest = new File(folderDecks+"\\extraction_5_shuffled_deck_x_150000_"+contDecks+".txt");
						copy(f,fDest);
					}
				}
				else if(f.isDirectory()) {
					copiaFiles(f.getAbsolutePath(),pathOutput);
				}
			}
		}
		
	}
	
	
	/*Metodo che va a leggere ogni file extraction_4_float_x_300000 e aggiunge "0." davanti ai numeri */
	public static void modificaDati(String path,String s) throws IOException {
		
		//File fileToBeModified = new File(path);
	   
		String zero = "0.";
		String data="";
        BufferedReader reader = new BufferedReader(new FileReader(path+"\\"+s));
        
        
        FileWriter writer = new FileWriter(path+"\\extraction_4_float_x_300000_0"+contatore+".txt",true);
        BufferedWriter bw = new BufferedWriter(writer);
        contatore++;
       //System.out.println("path: "+path); 
        
        try
        {
             
            String line;
             
            while ((line = reader.readLine()) != null) {
                //inputBuffer.append(line);
                //inputBuffer.append('\n');
            	data = zero+line;
            	bw.write(data);
            	bw.newLine();
            	//writer.write(data);
            	//System.out.println(data);
            }
            bw.close();
            
            
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
        //FileOutputStream fileOut = new FileOutputStream(path);
        //fileOut.write();
        //fileOut.close();
       // writer.close();
        
        
	}

}

package hugman.stock.fiche.file;

import hugman.stock.fiche.Fiche;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class FicheStockFileChooser extends JFileChooser
{
	private final Fiche fiche;

	public FicheStockFileChooser(Fiche fiche) {
		this.fiche = fiche;
	}

	@Override
	public void approveSelection() {
		File file = getSelectedFile();
		if(file != null) {
			FileFilter filter = getFileFilter();
			if(filter instanceof FileNameExtensionFilter) {
				String[] extensions = ((FileNameExtensionFilter) filter).getExtensions();

				boolean goodExt = false;
				for(String ext : extensions) {
					if(file.getName().toLowerCase().endsWith("." + ext.toLowerCase())) {
						goodExt = true;
						break;
					}
				}
				if(!goodExt) {
					file = new File(file.getParent(), file.getName() + "." + extensions[0]);
				}
			}

			if(file.exists()) {
				String okStr = "Écraser le fichier";
				String cancelStr = "Ne pas faire ça non";
				int ret =
						JOptionPane.showOptionDialog(this,
								"Le fichier %1 existe déjà !".replace("%1", file.getName()),
								"Fichier déjà existant",
								JOptionPane.OK_CANCEL_OPTION,
								JOptionPane.WARNING_MESSAGE,
								null,
								new Object[]{okStr, cancelStr},
								okStr);
				if(ret != JOptionPane.OK_OPTION) {
					return;
				}
			}
			setSelectedFile(file);
		}
		super.approveSelection();
	}

	public void showDialogAndSave(Component parent) {
		int userSelection = this.showSaveDialog(parent);

		if (userSelection == JFileChooser.APPROVE_OPTION) {
			this.saveToFile();
		}
	}

	public void saveToFile() {
		String output = CSVFormatter.format(fiche, ';');
		File fileToSave = this.getSelectedFile();
		try{
			fileToSave.createNewFile();
			FileOutputStream fos = new FileOutputStream(fileToSave);
			OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
			BufferedWriter bw = new BufferedWriter(osw);
			System.out.println(output);
			bw.write(output);
			bw.close();
		} catch(IOException ex) {
			ex.printStackTrace();
		}
		System.out.println("Saved as file: " + fileToSave.getAbsolutePath());
	}
}

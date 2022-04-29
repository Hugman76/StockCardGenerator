package me.hugman.acctools.action.ui;

import me.hugman.acctools.action.stock_card.StockCard;
import me.hugman.acctools.action.stock_card.file_format.StockCardFileFormat;
import me.hugman.acctools.action.stock_card.file_format.StockCardFileFormats;

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

public class StockCardFileChooser extends JFileChooser
{
	private final StockCard stockCard;
	private StockCardFileFormat stockCardFileFormat;

	public StockCardFileChooser(StockCard stockCard) {
		this.stockCard = stockCard;
		for(StockCardFileFormat format : StockCardFileFormats.FORMATS) {
			for(String extension : format.extensions()) {
				this.addChoosableFileFilter(new FileNameExtensionFilter(format.name(), extension));
			}
		}
		this.removeChoosableFileFilter(this.getAcceptAllFileFilter());
	}

	public StockCardFileFormat getFormat() {
		return stockCardFileFormat;
	}

	public void setFormat(StockCardFileFormat stockCardFileFormat) {
		this.stockCardFileFormat = stockCardFileFormat;
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

				StockCardFileFormat format = StockCardFileFormat.getFromFilter((FileNameExtensionFilter) filter);
				if(format == null) {
					JOptionPane.showMessageDialog(this, "Veuillez sélectionner un type de fichier correct.");
					return;
				}
				this.setFormat(format);
			}
			else {
				JOptionPane.showMessageDialog(this, "Veuillez sélectionner un type de fichier correct.");
				return;
			}

			if(file.exists()) {
				String okStr = "Écraser le fichier";
				String cancelStr = "Ne pas faire ça non";
				int ret = JOptionPane.showOptionDialog(this,
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

		if(userSelection == JFileChooser.APPROVE_OPTION) {
			this.saveToFile();
		}
	}

	public void saveToFile() {
		String output = this.getFormat().formatter().format(stockCard);
		File fileToSave = this.getSelectedFile();
		try {
			fileToSave.createNewFile();
			FileOutputStream fos = new FileOutputStream(fileToSave);
			OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
			BufferedWriter bw = new BufferedWriter(osw);
			bw.write(output);
			bw.close();
		} catch(IOException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Échec lors de la création du fichier.");
		}
		System.out.println("Saved stock card as file: " + fileToSave.getAbsolutePath());
	}
}

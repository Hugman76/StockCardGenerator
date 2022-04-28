package hugman.stock.fiche.file;

import hugman.stock.fiche.Fiche;

public class CSVFormatter
{
	public static String format(Fiche fiche, char delimiter) {
		StringBuilder sb = new StringBuilder();
		sb.append("Fiche de stocks%%%%%%%%%%\n");
		sb.append("Date%Entrées%%%Sorties%%%Stocks%%%\n");
		sb.append("%Quantités%Prix Uni%Montant%Quantités%Prix Uni%Montant%Quantités%Prix Uni%Montant%\n");
		for(Fiche.Ligne ligne : fiche.lignes()) {
			sb.append(ligne.date().getDate()).append("/").append(ligne.date().getMonth() + 1).append("/").append(ligne.date().getYear() + 1900).append("%");
			for(Fiche.Cellule cellule : new Fiche.Cellule[] { ligne.entree(), ligne.sortie(), ligne.stocks() }) {
				sb.append(cellule == null ? "%%%" : cellule.quantite() + "%" + cellule.prixUnitaire() + " €%" + cellule.montant() + " €%");
			}
			sb.append("\n");
		}
		return sb.toString().replace('%', delimiter);
	}
}

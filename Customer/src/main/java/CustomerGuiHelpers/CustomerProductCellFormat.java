package CustomerGuiHelpers;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import com.jfoenix.controls.JFXListCell;

import BasicCommonClasses.CartProduct;
import BasicCommonClasses.Sale;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * CustomerProductCellFormat - This class will format the cell content
 * 
 * @author idan atias
 * @author aviad
 * @author shimon Azulay
 * @since 2017-01-22
 */
public class CustomerProductCellFormat extends JFXListCell<CartProduct> {

	private boolean shouldEnableSale(CartProduct item, Sale s) {
		return s.isValid() && s.getAmountOfProducts() <= item.getTotalAmount();
	}
	
	@Override
	public void updateItem(CartProduct item, boolean empty) {
		super.updateItem(item, empty);

		if (item == null || empty) {
			setGraphic(null);
			setText(null);
			return;
		}

		boolean enableSale = shouldEnableSale(item, item.getCatalogProduct().getSale()),
				enableSpecialSale = shouldEnableSale(item, item.getCatalogProduct().getSpecialSale());
		HBox hbx = new HBox();
		VBox vbx = new VBox(5); // spacing = 5
	
		//vbox
		Label productName = new Label("Name: " + item.getCatalogProduct().getName());
		productName.getStyleClass().add("thisListLabel");
		//productName.setFont(new Font(20));
		Label productAmount = new Label("Amount: " + item.getTotalAmount());
		productAmount.getStyleClass().add("thisListLabel");
		//productAmount.setFont(new Font(20));
		Label productPrice ;
		if (enableSpecialSale) {
			Sale sale = item.getCatalogProduct().getSpecialSale();
			Double price = sale.getPrice() * item.getTotalAmount() / sale.getAmountOfProducts()
					+ item.getTotalAmount() * Double.valueOf(item.getCatalogProduct().getPrice())
							% sale.getAmountOfProducts();
			
			productPrice = new Label("Price: " + price + " nis");
		} else if (!enableSale)
			productPrice = new Label(
					"Price: " + item.getTotalAmount() * Double.valueOf(item.getCatalogProduct().getPrice()) + " nis");
		else {
			Sale sale = item.getCatalogProduct().getSale();
			Double price = sale.getPrice() * item.getTotalAmount() / sale.getAmountOfProducts()
					+ item.getTotalAmount() * Double.valueOf(item.getCatalogProduct().getPrice())
							% sale.getAmountOfProducts();
			productPrice = new Label("Price: " + price + " nis");
		}
		productPrice.getStyleClass().add("thisListLabel");
		//productPrice.setFont(new Font(20));
	    vbx.getChildren().addAll(productName, productAmount, productPrice);
	    vbx.setAlignment(Pos.CENTER_LEFT);	    

	    //image
	    long itemBarcode = item.getCatalogProduct().getBarcode();
	    URL imageUrl = null;
		try {
			imageUrl = new File("../Common/src/main/resources/ProductsPictures/" + itemBarcode + ".jpg").toURI().toURL();
		} catch (MalformedURLException e) {
			throw new RuntimeException();
		}
		Image image = new Image(imageUrl + "", 100, 100, true, false);
		ImageView productImage = new ImageView(image);
		ImageView sale;
		if (!enableSpecialSale)
			sale = new ImageView("/CustomerMainScreen/sale2.gif");
		else
			sale = new ImageView("/CustomerMainScreen/special.gif");
		
		sale.setFitHeight(80);
		sale.setFitWidth(120);
				
		hbx.setSpacing(10);
		
		if (enableSpecialSale || enableSale)
			sale.setVisible(true);
		else
			sale.setVisible(false);
		hbx.getChildren().addAll(vbx, productImage, sale);		
		setGraphic(hbx);
	}
}

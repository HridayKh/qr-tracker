package main;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Objects;

import javax.imageio.ImageIO;

import io.nayuki.qrcodegen.QrCode;
import io.nayuki.qrcodegen.QrCode.Ecc;

public class QR {

	// ECC = 0, 1, 2, 3 = L, M, Q, H
	public static String makeStatic(String Data, int ECC, int BgHex, int FgHex, int border, int scale) throws IOException {
		Ecc ecc;
		switch(ECC){
		case 0:
			ecc = QrCode.Ecc.LOW;
			break;
		case 1:
			ecc = QrCode.Ecc.MEDIUM;
			break;
		case 2:
			ecc = QrCode.Ecc.QUARTILE;
			break;
		case 3:
			ecc = QrCode.Ecc.HIGH;
			break;
		default:
			ecc = QrCode.Ecc.MEDIUM;
			break;
		}
		QrCode qrCode = QrCode.encodeText(Data, ecc);
		BufferedImage img = toImage(qrCode, scale, border, BgHex, FgHex);
		
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    ImageIO.write(img, "png", baos);
	    byte[] imageBytes = baos.toByteArray();
	    return Base64.getEncoder().encodeToString(imageBytes);
	}

	private static BufferedImage toImage(QrCode qr, int scale, int border, int lightColor, int darkColor) {
		Objects.requireNonNull(qr);
		if (scale <= 0 || border < 0) {
			throw new IllegalArgumentException("Value out of range");
		}
		if (border > Integer.MAX_VALUE / 2 || qr.size + border * 2L > Integer.MAX_VALUE / scale) {
			throw new IllegalArgumentException("Scale or border too large");
		}

		BufferedImage result = new BufferedImage((qr.size + border * 2) * scale, (qr.size + border * 2) * scale,
				BufferedImage.TYPE_INT_RGB);
		for (int y = 0; y < result.getHeight(); y++) {
			for (int x = 0; x < result.getWidth(); x++) {
				boolean color = qr.getModule(x / scale - border, y / scale - border);
				result.setRGB(x, y, color ? darkColor : lightColor);
			}
		}
		return result;
	}
	
}

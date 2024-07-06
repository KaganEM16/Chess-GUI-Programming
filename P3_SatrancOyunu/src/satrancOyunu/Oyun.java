package satrancOyunu;

import java.util.ArrayList;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class Oyun extends JFrame implements ActionListener
{
	JPanel[] paneller;
	JButton[] tasButonlari;
	JButton[] secenekButonlari;		
	JLabel[] sira;
	JLabel mesaj;
	ArrayList<Icon> beyazTaslar = new ArrayList<>();
	ArrayList<Icon> siyahTaslar = new ArrayList<>();
	static boolean oyunBittiMi = false;
	boolean siraBeyazda = true;
//	Rok işlemi için:
	ArrayList<JButton> beyazKale = new ArrayList<>();
	ArrayList<JButton> siyahKale = new ArrayList<>();
	boolean beyazSah , siyahSah;
	int bSahH = 0, sSahH = 0, rokTuru = 1;
//	Satranç tahtasının ayarlanması ve taş hareketleri için:
	int sayac = 0, satir = 1, mevcutTas;
	Icon butondaYazan;
//	Hamleyi bir geri alma işlemi için:
	boolean oyunBasladi, geriButonuKullanildi, tasYenildi, rokAtildi;
	int geriGidecekTasinIndexi, oncekiTasinKonumu;
	Icon yenenTas;
	
	public Oyun()
	{
		super("Satranç");
		int en = 600, boy = 600;
		this.setSize(en, boy);
		this.setLayout(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		
		paneller = new JPanel[3];
//		paneller[0] hazırlanışı:
		paneller[0] = new JPanel();
		paneller[0].setBounds(0, 0, 480, 480);
		paneller[0].setLayout(new GridLayout(8, 8));
		paneller[0].setBackground(Color.black);
		this.add(paneller[0]);
//		paneller[1] hazırlanışı:
		paneller[1] = new JPanel();
		paneller[1].setLayout(new GridLayout(4, 1));
		paneller[1].setBounds(480, 0, 110, 480);
		this.add(paneller[1]);
//		paneller[2] hazırlanışı:
		paneller[2] = new JPanel();
		paneller[2].setBounds(0, 480, en, 120);
		paneller[2].setBackground(Color.BLUE);
		this.add(paneller[2]);
		
//		paneller[0]'ın hazırlanması işlemleri:
		tasButonlari = new JButton[64];
		for(int i=0 ; i<tasButonlari.length ; i++) {
			tasBelirle(i);
			
			int row = i / 8;
            int col = i % 8;

            if ((row + col) % 2 == 0) {
                tasButonlari[i].setBackground(Color.WHITE);
            } else {
                tasButonlari[i].setBackground(Color.BLACK);
            }			
			
			tasButonlari[i].setSize(new Dimension(60, 60));			
			tasButonlari[i].addActionListener(this);
			paneller[0].add(tasButonlari[i]);
		}
		
//		paneller[1]'in hazırlanması işlemleri:		
		secenekButonlari = new JButton[2];
		secenekButonlari[0] = new JButton("Geri");
		secenekButonlari[1] = new JButton("Terk Et");
		for(int i=0 ; i<secenekButonlari.length ; i++) {
			secenekButonlari[i].setBackground(Color.RED);
			secenekButonlari[i].setForeground(Color.WHITE);
			secenekButonlari[i].addActionListener(this);
		}
		
		paneller[1].add(secenekButonlari[0]);
		sira = new JLabel[2];
		for(int i=0 ; i<sira.length ; i++) {
			if(i==0)
				sira[i] = new JLabel("Beyaz");
			else
				sira[i] = new JLabel("Oynar");
			sira[i].setForeground(Color.BLACK);
			sira[i].setFont(new Font("Verdana", Font.BOLD, 20));
			sira[i].setHorizontalAlignment(SwingConstants.CENTER);
			paneller[1].add(sira[i]);
		}
		paneller[1].add(secenekButonlari[1]);
		
//		paneller[2]'nin hazırlanması işlemleri:
		mesaj = new JLabel("Oyunumuza Hoşgeldiniz :)");
		mesaj.setForeground(Color.WHITE);
		mesaj.setFont(new Font("Verdana", Font.BOLD, 25));
		mesaj.setHorizontalAlignment(SwingConstants.CENTER);
		paneller[2].add(mesaj);
		
		this.setVisible(true);
	}
	
	public void tasBelirle(int i)
	{
		ImageIcon icon;
		int iconEni = 45, iconBoyu = 60;
		
		if(i==0 || i==7) {
			icon = new ImageIcon("Satranç Taşları/Kırmızı Kale.png");
			Image image = icon.getImage();
			Image image2 = image.getScaledInstance(iconEni, iconBoyu, Image.SCALE_SMOOTH);
			ImageIcon kullanilacakIcon = new ImageIcon(image2);
			tasButonlari[i] = new JButton(kullanilacakIcon);
			siyahTaslar.add(tasButonlari[i].getIcon());
			siyahKale.add(tasButonlari[i]);
		}else if(i== 1 || i== 6) {
			icon = new ImageIcon("Satranç Taşları/Kırmızı At.png");
			Image image = icon.getImage();
			Image image2 = image.getScaledInstance(iconEni, iconBoyu, Image.SCALE_SMOOTH);
			ImageIcon kullanilacakIcon = new ImageIcon(image2);
			tasButonlari[i] = new JButton(kullanilacakIcon);
			siyahTaslar.add(tasButonlari[i].getIcon());
		}else if(i== 2 || i== 5) {
			icon = new ImageIcon("Satranç Taşları/Kırmızı Fil.png");
			Image image = icon.getImage();
			Image image2 = image.getScaledInstance(iconEni, iconBoyu, Image.SCALE_SMOOTH);
			ImageIcon kullanilacakIcon = new ImageIcon(image2);
			tasButonlari[i] = new JButton(kullanilacakIcon);
			siyahTaslar.add(tasButonlari[i].getIcon());
		}else if(i== 3) {
			icon = new ImageIcon("Satranç Taşları/Kırmızı Vezir.png");
			Image image = icon.getImage();
			Image image2 = image.getScaledInstance(iconEni, iconBoyu, Image.SCALE_SMOOTH);
			ImageIcon kullanilacakIcon = new ImageIcon(image2);
			tasButonlari[i] = new JButton(kullanilacakIcon);
			siyahTaslar.add(tasButonlari[i].getIcon());
		}else if(i== 4) {
			icon = new ImageIcon("Satranç Taşları/Kırmızı Şah.png");
			Image image = icon.getImage();
			Image image2 = image.getScaledInstance(iconEni, iconBoyu, Image.SCALE_SMOOTH);
			ImageIcon kullanilacakIcon = new ImageIcon(image2);
			tasButonlari[i] = new JButton(kullanilacakIcon);
			siyahTaslar.add(tasButonlari[i].getIcon());	
		}else if(i >= 8 && i <= 15) {
			icon = new ImageIcon("Satranç Taşları/Kırmızı Piyon.png");
			Image image = icon.getImage();
			Image image2 = image.getScaledInstance(iconEni, iconBoyu, Image.SCALE_SMOOTH);
			ImageIcon kullanilacakIcon = new ImageIcon(image2);
			tasButonlari[i] = new JButton(kullanilacakIcon);
			siyahTaslar.add(tasButonlari[i].getIcon());
		}else if(i==56 || i==63) {
			icon = new ImageIcon("Satranç Taşları/Beyaz Kale.png");
			Image image = icon.getImage();
			Image image2 = image.getScaledInstance(iconEni, iconBoyu, Image.SCALE_SMOOTH);
			ImageIcon kullanilacakIcon = new ImageIcon(image2);
			tasButonlari[i] = new JButton(kullanilacakIcon);
			beyazTaslar.add(tasButonlari[i].getIcon());
			beyazKale.add(tasButonlari[i]);
		}else if(i== 57 || i== 62) {
			icon = new ImageIcon("Satranç Taşları/Beyaz At.png");
			Image image = icon.getImage();
			Image image2 = image.getScaledInstance(iconEni, iconBoyu, Image.SCALE_SMOOTH);
			ImageIcon kullanilacakIcon = new ImageIcon(image2);
			tasButonlari[i] = new JButton(kullanilacakIcon);
			beyazTaslar.add(tasButonlari[i].getIcon());
		}else if(i== 58 || i== 61) {
			icon = new ImageIcon("Satranç Taşları/Beyaz Fil.png");
			Image image = icon.getImage();
			Image image2 = image.getScaledInstance(iconEni, iconBoyu, Image.SCALE_SMOOTH);
			ImageIcon kullanilacakIcon = new ImageIcon(image2);
			tasButonlari[i] = new JButton(kullanilacakIcon);
			beyazTaslar.add(tasButonlari[i].getIcon());
		}else if(i== 59) {
			icon = new ImageIcon("Satranç Taşları/Beyaz Vezir.png");
			Image image = icon.getImage();
			Image image2 = image.getScaledInstance(iconEni, iconBoyu, Image.SCALE_SMOOTH);
			ImageIcon kullanilacakIcon = new ImageIcon(image2);
			tasButonlari[i] = new JButton(kullanilacakIcon);
			beyazTaslar.add(tasButonlari[i].getIcon());
		}else if(i== 60) {
			icon = new ImageIcon("Satranç Taşları/Beyaz Şah.png");
			Image image = icon.getImage();
			Image image2 = image.getScaledInstance(iconEni, iconBoyu, Image.SCALE_SMOOTH);
			ImageIcon kullanilacakIcon = new ImageIcon(image2);
			tasButonlari[i] = new JButton(kullanilacakIcon);
			beyazTaslar.add(tasButonlari[i].getIcon());
		}else if(i >= 48 && i <= 55) {
			icon = new ImageIcon("Satranç Taşları/Beyaz Piyon.png");
			Image image = icon.getImage();
			Image image2 = image.getScaledInstance(iconEni, iconBoyu, Image.SCALE_SMOOTH);
			ImageIcon kullanilacakIcon = new ImageIcon(image2);
			tasButonlari[i] = new JButton(kullanilacakIcon);
			beyazTaslar.add(tasButonlari[i].getIcon());
		}else {
			tasButonlari[i] = new JButton("");
		}
	}	

	@Override
	public void actionPerformed(ActionEvent e)
	{
		JButton basilanButon = (JButton)e.getSource();		
		
		if(basilanButon == secenekButonlari[1]){
			if(siraBeyazda)
				mesaj.setText("SİYAH TAŞLAR KAZANDI");
			else
				mesaj.setText("BEYAZ TAŞLAR KAZANDI");
			oyunBittiMi = true;
		}
		
		if(!oyunBittiMi) {			
			sayac ++;
			
			if(basilanButon == secenekButonlari[0] && oyunBasladi){ // Geri alma işlemi için:
				if(!geriButonuKullanildi) {
					if(rokAtildi) {
						if(rokTuru == 3) {
							tasButonlari[0].setIcon(tasButonlari[3].getIcon());
							tasButonlari[3].setIcon(null);
							sSahH = 0;
						}else if(rokTuru == 4) {
							tasButonlari[7].setIcon(tasButonlari[5].getIcon());
							tasButonlari[5].setIcon(null);
							sSahH = 0;
						}else if(rokTuru == 1) {
							tasButonlari[56].setIcon(tasButonlari[59].getIcon());
							tasButonlari[59].setIcon(null);
							bSahH = 0;
						}else if(rokTuru == 2){
							tasButonlari[63].setIcon(tasButonlari[61].getIcon());
							tasButonlari[61].setIcon(null);
							bSahH = 0;
						}
						rokTuru = 0;
					}
					tasButonlari[oncekiTasinKonumu].setIcon(tasButonlari[geriGidecekTasinIndexi].getIcon());
					if(tasYenildi) {
						tasButonlari[geriGidecekTasinIndexi].setIcon(yenenTas);
						yenenTas = null;						
					}else 
						tasButonlari[geriGidecekTasinIndexi].setIcon(null);					
					if(siraBeyazda) {
						siraBeyazda = false;
						sira[0].setText("Siyah");
					}else {
						siraBeyazda = true;
						sira[0].setText("Beyaz");
					}
					geriButonuKullanildi = true;
				}else {
					JOptionPane.showMessageDialog(null, "Geri butonu ile yalnızca bir önceki hamleye gidebilirsiniz.");					
				}				
				sayac=0;					
			}else if(sayac==1) {
				if(tasYenildi) {
					tasYenildi = false;
				}
				for(int i=0 ; i< tasButonlari.length ; i++) 
				{
					if(basilanButon == tasButonlari[i]) {
						oncekiTasinKonumu = i;
						if(tasButonlari[i].getIcon() == null) {
							sayac--;
						}else {
							if(basilanButon == tasButonlari[4] && sSahH == 0) {
								siyahSah = true;
							}else if(basilanButon == tasButonlari[60] && bSahH == 0) {
								beyazSah = true;
							}
							
							if(siraBeyazda && beyazTaslar.contains(tasButonlari[i].getIcon())) {
								butondaYazan = tasButonlari[i].getIcon();
								mevcutTas = i;
							}else if(!siraBeyazda && siyahTaslar.contains(tasButonlari[i].getIcon())){
								butondaYazan = tasButonlari[i].getIcon();
								mevcutTas = i;
							}else {
								sayac = 0;
							}
						}
					}
				}				
			}else if(sayac == 2 && butondaYazan != null) // "&& butondaYazan != null" kısmına gerek yok. 
			{
				if(rokAtildi)
					rokAtildi = false;
				for(int i=0 ; i< tasButonlari.length ; i++) 
				{
					if(basilanButon == tasButonlari[i]) {
						geriGidecekTasinIndexi = i;
						geriButonuKullanildi = false;
						if((beyazSah || siyahSah) && (bSahH ==0 || sSahH == 0)) {  // ROK Durumu
							if(beyazSah) {
								if(basilanButon == tasButonlari[58]) {
									tasButonlari[i].setIcon(butondaYazan);
									tasButonlari[mevcutTas].setIcon(null);
									if(beyazKale.contains(tasButonlari[56])){
										tasButonlari[59].setIcon(tasButonlari[56].getIcon());
										tasButonlari[56].setIcon(null);
										rokTuru = 1;
									}									
								}else if(basilanButon == tasButonlari[62]){
									tasButonlari[i].setIcon(butondaYazan);
									tasButonlari[mevcutTas].setIcon(null);
									if(beyazKale.contains(tasButonlari[63])){
										tasButonlari[61].setIcon(tasButonlari[63].getIcon());
										tasButonlari[63].setIcon(null);
										rokTuru = 2;
									}									
								}
								siraBeyazda = false;
								sira[0].setText("Siyah");
								beyazSah = false;
								bSahH = 1;
							}else {
								if(basilanButon == tasButonlari[2]) {
									tasButonlari[i].setIcon(butondaYazan);
									tasButonlari[mevcutTas].setIcon(null);
									if(siyahKale.contains(tasButonlari[0])){
										tasButonlari[3].setIcon(tasButonlari[0].getIcon());
										tasButonlari[0].setIcon(null);
										rokTuru = 3;
									}									
								}else if(basilanButon == tasButonlari[6]){
									tasButonlari[i].setIcon(butondaYazan);
									tasButonlari[mevcutTas].setIcon(null);
									if(siyahKale.contains(tasButonlari[7])){
										tasButonlari[5].setIcon(tasButonlari[7].getIcon());
										tasButonlari[7].setIcon(null);
										rokTuru = 4;
									}									
								}
								siraBeyazda = true;
								sira[0].setText("Beyaz");
								siyahSah=false;
								sSahH = 1;
							}
							rokAtildi = true;
						}else if(tasButonlari[i].getIcon() == null) { // Boş karelere gitmek için buraya girilir.
							tasButonlari[i].setIcon(butondaYazan);
							tasButonlari[mevcutTas].setIcon(null);							
							if(siraBeyazda) {
								siraBeyazda = false;
								sira[0].setText("Siyah");
							}else {
								siraBeyazda = true;
								sira[0].setText("Beyaz");
							}
						}else { // Taş yemek için buraya girilir
							if((siraBeyazda && siyahTaslar.contains(tasButonlari[i].getIcon())) || (!siraBeyazda && beyazTaslar.contains(tasButonlari[i].getIcon()))) {
								yenenTas = tasButonlari[i].getIcon();
								tasYenildi = true;
								tasButonlari[i].setIcon(null);
								tasButonlari[i].setIcon(butondaYazan);
								tasButonlari[mevcutTas].setIcon(null);
								if(siraBeyazda) {
									siraBeyazda = false;
									sira[0].setText("Siyah");
								}else {
									siraBeyazda = true;
									sira[0].setText("Beyaz");
								}
							}
						}
						oyunBasladi = true;
					}
				}
				sayac = 0;
			}else {
				sayac = 0;
			}
		}
	}	
}

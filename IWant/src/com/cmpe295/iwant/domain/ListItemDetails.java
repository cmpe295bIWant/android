package com.cmpe295.iwant.domain;

public class ListItemDetails {
	 private String name;
	 private int lowestprice;
     private int image;
    
     public String getName()
            {
            return name;
            }
     public void setName(String name)
            {
            this.name = name;
            }
     
     public int getPrice()
     {
    	 return lowestprice;
     }
     public void setPrice(int lowestprice)
     {
    	 this.lowestprice = lowestprice;
     }


     public int getImage()
            {
            return image;
            }
     public void setImage(int image)
            {
            this.image = image;
            }
}

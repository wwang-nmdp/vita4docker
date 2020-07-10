package com.nmdp.vita;

import java.util.ArrayList;
import java.util.List;

public enum Base {
    A,
    T,
    C,
    G;

   public List<Base> getOtherBase(){
       List<Base> baseList = new ArrayList<>();
       baseList.add(Base.A);
       baseList.add(Base.T);
       baseList.add(Base.C);
       baseList.add(Base.G);
       baseList.remove(this);
       return baseList;
   }
}

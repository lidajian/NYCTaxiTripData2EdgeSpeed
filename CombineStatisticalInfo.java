import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Date;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map.Entry;

public class CombineStatisticalInfo{

    private static class AnHour{
        public double sum;
        public double sum2;
        public double min;
        public double max;
        public long size;

        public AnHour(){
            sum = 0.0;
            sum2 = 0.0;
            min = 100;
            max = -1;
            size = 0;
        }

        public void put(double speed){
            sum += speed;
            sum2 += speed * speed;
            size++;
            if(min > speed) min = speed;
            if(max < speed) max = speed;
        }
    }

    private static class DayInWeek{

        final private static int OFFSET_SUM = 48;
        final private static int OFFSET_SUM2 = 96;
        final private static int OFFSET_MIN = 144;
        final private static int OFFSET_MAX = 192;

        public AnHour [] weekday = new AnHour[24];
        public AnHour [] weekend = new AnHour[24];

        public DayInWeek(){
            for(int i = 0; i < 24; i++){
                weekday[i] = new AnHour();
                weekend[i] = new AnHour();
            }
        }

        public DayInWeek(String [] parts) throws ParseException{
            for(int i = 0; i < 24; i++){
                weekday[i] = new AnHour();
                weekend[i] = new AnHour();
            }
            for(int i = 1; i <= 24; i++){
                if(!parts[i].equals("NA")){
                    AnHour h = weekday[i-1];
                    h.size = Long.parseLong(parts[i]);
                    h.sum = Double.parseDouble(parts[i+OFFSET_SUM]);
                    h.sum2 = Double.parseDouble(parts[i+OFFSET_SUM2]);
                    h.min = Double.parseDouble(parts[i+OFFSET_MIN]);
                    h.max = Double.parseDouble(parts[i+OFFSET_MAX]);
                }
            }
            for(int i = 25; i <= 48; i++){
                if(!parts[i].equals("NA")){
                    AnHour h = weekend[i-25];
                    h.size = Long.parseLong(parts[i]);
                    h.sum = Double.parseDouble(parts[i+OFFSET_SUM]);
                    h.sum2 = Double.parseDouble(parts[i+OFFSET_SUM2]);
                    h.min = Double.parseDouble(parts[i+OFFSET_MIN]);
                    h.max = Double.parseDouble(parts[i+OFFSET_MAX]);
                }
            }
        }

        public void put(Calendar date, double speed){
            switch(date.get(Calendar.DAY_OF_WEEK)){
                case Calendar.SUNDAY:
                case Calendar.SATURDAY:
                    weekend[date.get(Calendar.HOUR_OF_DAY)].put(speed);
                    break;
                default:
                    weekday[date.get(Calendar.HOUR_OF_DAY)].put(speed);
            }
        }

        public void add(String [] parts) throws ParseException{
            double temp;
            for(int i = 1; i <= 24; i++){
                if(!parts[i].equals("NA")){
                    AnHour h = weekday[i-1];
                    h.size += Long.parseLong(parts[i]);
                    h.sum += Double.parseDouble(parts[i+OFFSET_SUM]);
                    h.sum2 += Double.parseDouble(parts[i+OFFSET_SUM2]);
                    temp = Double.parseDouble(parts[i+OFFSET_MIN]);
                    if(temp < h.min) h.min = temp;
                    temp = Double.parseDouble(parts[i+OFFSET_MAX]);
                    if(temp > h.max) h.max = temp;
                }
            }
            for(int i = 25; i <= 48; i++){
                if(!parts[i].equals("NA")){
                    AnHour h = weekend[i-25];
                    h.size += Long.parseLong(parts[i]);
                    h.sum += Double.parseDouble(parts[i+OFFSET_SUM]);
                    h.sum2 += Double.parseDouble(parts[i+OFFSET_SUM2]);
                    temp = Double.parseDouble(parts[i+OFFSET_MIN]);
                    if(temp < h.min) h.min = temp;
                    temp = Double.parseDouble(parts[i+OFFSET_MAX]);
                    if(temp > h.max) h.max = temp;
                }
            }
        }

        public String toString(){
            StringBuffer sbsum = new StringBuffer();
            StringBuffer sbsum2 = new StringBuffer();
            StringBuffer sbmin = new StringBuffer();
            StringBuffer sbmax = new StringBuffer();
            StringBuffer sbsize = new StringBuffer();
            AnHour temp;
            for(int i = 0; i < 24; i++){
                temp = weekday[i];
                if(temp.size == 0){
                    sbsum.append("NA\t");
                    sbsum2.append("NA\t");
                    sbmin.append("NA\t");
                    sbmax.append("NA\t");
                    sbsize.append("NA\t");
                }else{
                    sbsum.append(temp.sum+"\t");
                    sbsum2.append(temp.sum2+"\t");
                    sbmin.append(temp.min+"\t");
                    sbmax.append(temp.max+"\t");
                    sbsize.append(temp.size+"\t");
                }
            }
            for(int i = 0; i < 24; i++){
                temp = weekend[i];
                if(temp.size == 0){
                    sbsum.append("NA\t");
                    sbsum2.append("NA\t");
                    sbmin.append("NA\t");
                    sbmax.append("NA\t");
                    sbsize.append("NA\t");
                }else{
                    sbsum.append(temp.sum+"\t");
                    sbsum2.append(temp.sum2+"\t");
                    sbmin.append(temp.min+"\t");
                    sbmax.append(temp.max+"\t");
                    sbsize.append(temp.size+"\t");
                }
            }
            return sbsize.toString() + sbsum.toString() + sbsum2.toString() + sbmin.toString() + sbmax.toString();
        }
    }

    public static void main(String args[]){
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String line;
            String edgeid = null;
            DayInWeek dayinweek = null;
            HashMap<String, DayInWeek> records = new HashMap<String, DayInWeek>();
            while((line = br.readLine())!=null){
                try{
                    String [] parts = line.split("\t");
                    edgeid = parts[0];

                    if((dayinweek = records.get(edgeid)) == null){
                        dayinweek = new DayInWeek(parts);
                        records.put(edgeid, dayinweek);
                    }else{
                        dayinweek.add(parts);
                    }



                }catch(ParseException e){
                    e.printStackTrace();
                }
            }
            Iterator iter = records.entrySet().iterator();
            while(iter.hasNext()){
                Entry entry = (Entry)iter.next();
                edgeid = (String) entry.getKey();
                dayinweek = (DayInWeek) entry.getValue();
                System.out.println(edgeid+"\t"+dayinweek.toString());
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}

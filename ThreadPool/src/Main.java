import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final List<Integer> tekSayilar = new ArrayList<>();
    private static final List<Integer> ciftSayilar = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {

        List<Integer> sayilar = new ArrayList<>();
        for (int i = 1; i <= 10000; i++) {
            sayilar.add(i);
        }


        List<List<Integer>> parcalar = new ArrayList<>();
        int size = sayilar.size() / 4;

        for (int i = 0; i < 4; i++) {
            int start = i * size;
            int end = (i == 3) ? sayilar.size() : (i + 1) * size;
            parcalar.add(sayilar.subList(start, end));
        }


        Thread[] threads = new Thread[4];

        for (int i = 0; i < 4; i++) {
            final List<Integer> parcala = parcalar.get(i);
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (Integer sayi : parcala) {
                        if (sayi % 2 == 0) {
                            synchronized (ciftSayilar) {
                                ciftSayilar.add(sayi);
                            }
                        } else {
                            synchronized (tekSayilar) {
                                tekSayilar.add(sayi);
                            }
                        }
                    }
                }
            });
            threads[i].start();
        }


        for (Thread thread : threads) {
            thread.join();
        }


        System.out.println("Çift Sayılar:");
        System.out.println(ciftSayilar);
        System.out.println("Tek Sayılar:");
        System.out.println(tekSayilar);
    }
}
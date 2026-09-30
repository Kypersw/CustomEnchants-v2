package me.plugin.customenchants.managers;

import me.plugin.customenchants.models.CustomEnchant;
import me.plugin.customenchants.models.CustomEnchant.Category;

import java.util.*;

public class EnchantManager {

    private final Map<String, CustomEnchant> enchantments = new HashMap<>();

    public void loadEnchants() {
        // --- 1. SALDIRI (12 Büyü) ---
        register("vampirism", "Vampirlik", Category.ATTACK, 5, 10, List.of("Hasar verdikçe can yeniler."));
        register("zehir_ok", "Zehirli Ok", Category.ATTACK, 3, 8, List.of("Düşmanı zehirler."));
        register("yildirim", "Çekiç Darbesi", Category.ATTACK, 3, 12, List.of("Düşmana yıldırım düşürür."));
        register("alev_patlamasi", "Alev Patlaması", Category.ATTACK, 4, 9, List.of("Alan alev hasarı verir."));
        register("kritik_vurus", "Derin Yaralama", Category.ATTACK, 5, 11, List.of("Kritik vuruş şansını artırır."));
        register("kanama", "Kanlı Bıçak", Category.ATTACK, 3, 7, List.of("Zamanla hasar veren kanama uygular."));
        register("zırh_kiran", "Zırh Kıran", Category.ATTACK, 4, 14, List.of("Düşmanın zırh dayanıklılığını düşürür."));
        register("buz_dokunusu", "Buzlu Temas", Category.ATTACK, 3, 8, List.of("Düşmanı yavaşlatır."));
        register("karanlik_darbe", "Gölge Hasarı", Category.ATTACK, 5, 10, List.of("Körlük etkisi verir."));
        register("infazci", "İnfazcı", Category.ATTACK, 3, 15, List.of("Düşmanın canı azaldıkça daha çok vurur."));
        register("ruhkapan", "Ruh Kapanı", Category.ATTACK, 5, 9, List.of("Yaratıklardan ekstra tecrübe kazandırır."));
        register("savurucu", "Gök Gürültüsü", Category.ATTACK, 2, 13, List.of("Düşmanı havaya fırlatır."));

        // --- 2. SAVUNMA (12 Büyü) ---
        register("kemik_zırh", "Kemik Zırh", Category.DEFENSE, 5, 8, List.of("Gelen hasarı yüzde olarak azaltır."));
        register("yansitma", "Hasar Yansıtma", Category.DEFENSE, 3, 12, List.of("Saldırana hasarın bir kısmını iletir."));
        register("son_sans", "Son Şans", Category.DEFENSE, 1, 20, List.of("Ölümcül darbeden korur ve can verir."));
        register("ateş_kalkanı", "Ateş Kalkanı", Category.DEFENSE, 3, 7, List.of("Ateş ve lav hasarını engeller."));
        register("ok_savar", "Ok Savar", Category.DEFENSE, 4, 9, List.of("Mermilerden alınan hasarı azaltır."));
        register("buz_kalkani", "Buz Kalkanı", Category.DEFENSE, 3, 10, List.of("Saldıranı dondurur."));
        register("zehir_bagisikligi", "Panzehir", Category.DEFENSE, 1, 6, List.of("Zehir ve kötü efektleri engeller."));
        register("patlama_korumasi", "Sarsıntı Engelleyici", Category.DEFENSE, 4, 8, List.of("Patlama hasarlarını düşürür."));
        register("kutsal_koruma", "Kutsal Hale", Category.DEFENSE, 3, 11, List.of("Periyodik olarak koruma kalkanı üretir."));
        register("sifali_dokunus", "Rejenerasyon", Category.DEFENSE, 3, 10, List.of("Savaştayken can yenilemesini artırır."));
        register("demir_ten", "Demir Ten", Category.DEFENSE, 5, 12, List.of("Direnç etkisi sağlar."));
        register("ruh_kalkani", "Ruh Kalkanı", Category.DEFENSE, 2, 18, List.of("Hasar alındığında deneyim puanı harcayarak canlı tutar."));

        // --- 3. YARDIMCI / UTILITY (12 Büyü) ---
        register("otomatik_pisirme", "Eritici", Category.UTILITY, 1, 10, List.of("Madencilik yaparken cevherleri eritir."));
        register("genis_kazma", "Alan Kazıcı (3x3)", Category.UTILITY, 3, 25, List.of("3x3 alan kazar."));
        register("mıknatıs", "Mıknatıs", Category.UTILITY, 3, 8, List.of("Etraftaki eşyaları oyuncuya çeker."));
        register("gece_gorus", "Gece Görüşü", Category.UTILITY, 1, 5, List.of("Sürekli gece görüşü sağlar."));
        register("hizli_kırma", "Acele", Category.UTILITY, 3, 9, List.of("Kazma hızını artırır."));
        register("su_altı_kazı", "Deniz Adamı", Category.UTILITY, 2, 7, List.of("Su altında hızlı kazı sağlar."));
        register("ipeksi_dokunus", "Hasarlı Hasat", Category.UTILITY, 1, 15, List.of("Spawner ve özel blokları düşürür."));
        register("ciftci", "Bereketli Hasat", Category.UTILITY, 3, 6, List.of("Ekinleri otomatik yeniden eker."));
        register("oduncu", "Ağaç Deviren", Category.UTILITY, 2, 12, List.of("Ağacın tüm gövdesini tekte keser."));
        register("isik_yolu", "Işık Yolu", Category.UTILITY, 1, 8, List.of("Karanlık yerlerde yürürken geçici ışık kaynağı koyar."));
        register("teleport_ok", "Işınlanma Oku", Category.UTILITY, 1, 14, List.of("Atılan okun düştüğü yere ışınlar."));
        register("tamirci", "Ruhani Tamir", Category.UTILITY, 3, 10, List.of("Tecrübe topladıkça eşya dayanıklılığını artırır."));

        // --- 4. PASİF (12 Büyü) ---
        register("hiz", "Rüzgar Koşusu", Category.PASSIVE, 3, 7, List.of("Hareket hızını artırır."));
        register("yuksek_ziplama", "Yaylı Çizme", Category.PASSIVE, 3, 6, List.of("Daha yükseğe zıplama sağlar."));
        register("doygunluk", "Besleyici", Category.PASSIVE, 2, 10, List.of("Açlık barının düşmesini yavaşlatır."));
        register("su_yuruyusu", "Buzul Yürüyüşçü", Category.PASSIVE, 2, 9, List.of("Su üzerinde yürümeyi sağlar."));
        register("yumusak_dusus", "Tüy Düşüşü", Category.PASSIVE, 4, 5, List.of("Düşüş hasarını engeller."));
        register("görünmezlik", "Gölge Pelerini", Category.PASSIVE, 1, 20, List.of("Eğilindiğinde geçici görünmezlik verir."));
        register("oksijen", "Derin Nefes", Category.PASSIVE, 3, 6, List.of("Su altında nefesi uzatır."));
        register("saglik_artisi", "Can Arttırıcı", Category.PASSIVE, 5, 15, List.of("Maksimum can miktarını yükseltir."));
        register("sans", "Şanslı Adım", Category.PASSIVE, 3, 11, List.of("Nadir eşya bulma şansını artırır."));
        register("ates_yuruyusu", "Ateş Yürüyüşçü", Category.PASSIVE, 1, 12, List.of("Lav üzerinde yürümeyi sağlar."));
        register("sarsilmaz", "Ağır Adım", Category.PASSIVE, 2, 8, List.of("Geri savrulmayı azaltır."));
        register("kudret", "Odaklanma", Category.PASSIVE, 3, 10, List.of("Sabit dururken ek hasar direnci verir."));

        // --- 5. ÖZEL / APEX (12 Büyü) ---
        register("zaman_durdurma", "Zaman Bükücü", Category.APEX, 1, 50, List.of("Etraftaki yaratıkları ve oyuncuları dondurur."));
        register("ejderha_nefesi", "Ejderha Gazabı", Category.APEX, 3, 40, List.of("Alan etkili ejderha alevi püskürtür."));
        register("karadelik", "Kara Delik", Category.APEX, 1, 45, List.of("Düşmanları tek bir noktaya çeken vakum oluşturur."));
        register("tanri_modu", "Aegis Kalkanı", Category.APEX, 1, 60, List.of("5 saniye boyunca tüm hasarları sıfırlar."));
        register("meteor", "Kıyamet Meteorü", Category.APEX, 2, 50, List.of("Gökyüzünden meteor yağdırır."));
        register("ölümsüzlük", "Anka Kuşu", Category.APEX, 1, 55, List.of("Öldüğünüzde %100 canlılıkla yeniden doğarsınız."));
        register("zihin_kontrolu", "Kaos Dokunuşu", Category.APEX, 2, 35, List.of("Yaratıkların birbirine saldırmasını sağlar."));
        register("yildirim_firtinasi", "Zeus Gazabı", Category.APEX, 3, 40, List.of("Geniş bir alana sürekli yıldırım düşürür."));
        register("boyut_kapi", "Işınlanma Kapısı", Category.APEX, 1, 30, List.of("Geçici solucan deliği açar."));
        register("ruh_emici", "Ruh Yiyici", Category.APEX, 3, 35, List.of("Öldürülen her düşmandan kalıcı güç kazanımı (Geçici)."));
        register("yercekimi", "Sıfır Yerçekimi", Category.APEX, 2, 38, List.of("Hedefleri yerçekimsiz alanda uçurur."));
        register("mutlak_sifir", "Mutlak Sıfır", Category.APEX, 1, 48, List.of("Geniş alandaki tüm düşmanları buza hapseder."));
    }

    private void register(String id, String name, Category category, int maxLevel, int baseCostExp, List<String> lore) {
        enchantments.put(id, new CustomEnchant(id, name, category, maxLevel, baseCostExp, lore));
    }

    public Map<String, CustomEnchant> getEnchantments() { return enchantments; }
    public CustomEnchant getEnchant(String id) { return enchantments.get(id); }
}
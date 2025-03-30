from typing import List, Dict, Set

class TomatoExpertSystem:
    def __init__(self):
        self.mappings = {
            "conditions": {
                "C01": "Batang dan daun atas layu",
                "C02": "Bercak coklat sampai hitam pada daun",
                "C03": "Ada bercak kecil berwarna coklat pada buah",
                "C04": "Ada bercak kecil berair dan membuat cekung pada buah",
                "C05": "Pada pangkal buah dekat tangkai ada becak ungu",
                "C06": "Daun keriting dan kering",
                "C07": "Batang tampak kering memanjang berwarna abu-abu",
                "C08": "Ada lubang buah setiap tomat",
                "C09": "Batang mudah patah",
                "C10": "Daun keriting dan kerdil, melengkung ke bawah, daun terlihat seperti terselubungi tepung putih",
                "C11": "Daun menyempit seperti pita, mengecil dan menggulung ke atas",
                "C12": "Buah busuk dan jika dibuka ada belatung"
            },
            "diseases": {
                "D01": "Penyakit Layu Bakteri",
                "D02": "Busuk Lunak Bakteri",
                "D03": "Penyakit Cekik",
                "D04": "Busuk Daun",
                "D05": "Bercak Kuning",
                "D06": "Layu Cendawan",
                "D07": "Penyakit Ujung Keriting",
                "D08": "TMV (Tomato Mozaik Virus)",
                "D09": "PVX (Potato Virus X)",
                "D10": "PVY (Potato Virus Y)",
                "D11": "Busuk Ujung Buah",
                "D12": "Pecah Buah"
            },
            "solutions": {
                "S01": "Memakai benih yang resisten",
                "S02": "Memakai mulsa plastik",
                "S03": "Menjauhi budidaya tomat pada tempat yang terkena jamur",
                "S04": "Memberi jeda yang lama pada lahan hingga ditanami kembali",
                "S05": "Harus cepat dicabut dan dibakar, jangan dipendam, memutuskan siklus tanaman tomat",
                "S06": "Memakai varietas unggul, memakai varietas bebas jamur",
                "S07": "Penyemprotan memakai Fungisida",
                "S08": "Memakai kayu untuk menyokong tanaman supaya buah tak menyentuh tanah",
                "S09": "Merotasi pembibitan tanaman",
                "S10": "Penyemprotan memakai bakterisida yang memakai antibiotik dengan dosis sesuai panduan",
                "S11": "Membuang ulat dan telur lalu membakarnya",
                "S12": "Menjaga kebersihan kebun dari Gulma serta semak belukar",
                "S13": "Membuat perangkat ultraviolet",
                "S14": "Penyemprotan memakai insektisida",
                "S15": "Mengolah tanah dengan baik",
                "S16": "Memakai Mulva Plastik Perak",
                "S17": "Memakai Mulva Plastik Jerami atau Mulsa Kuning",
                "S18": "Membalik tanah dan Membiarkan Terkena Sinar Matahari Selama Beberapa Hari"
            }
        }
        
        self.rules = [
            {
                "id": "R01", 
                "conditions": {"C01", "C02", "C07", "C09"}, 
                "disease": "D01", 
                "solutions": ["S01", "S02", "S03", "S04"], 
            },
        ]

    def forward_chain(self, input_conditions: Set[str]) -> List[Dict]:
        results = []
        activated_rules = set()
        
        # Proses forward chaining
        while True:
            new_rules_activated = False
            
            for rule in self.rules:
                if rule["id"] not in activated_rules:
                    if rule["conditions"].issubset(input_conditions):
                        # Tambahkan kesimpulan
                        disease_desc = self.mappings["diseases"][rule["disease"]]
                        solution_desc = [self.mappings["solutions"][s] for s in rule["solutions"]]
                        condition_desc = [self.mappings["conditions"][c] for c in rule["conditions"]]
                        
                        results.append({
                            "rule_id": rule["id"],
                            "disease": disease_desc,
                            "solutions": solution_desc,
                            "matched_conditions": condition_desc
                        })
                        
                        activated_rules.add(rule["id"])
                        new_rules_activated = True
            
            if not new_rules_activated:
                break
        
        return results

    def get_mappings(self) -> Dict:
        return self.mappings
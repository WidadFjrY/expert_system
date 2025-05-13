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
                "C10": "Daun keriting, kerdil, melengkung ke bawah, dan daun terlihat seperti terselubungi tepung putih",
                "C11": "Daun menyempit seperti pita, mengecil dan menggulung ke atas",
                "C12": "Buah busuk dan jika dibuka ada belatung",
                "C13": "Ada retakan dan sobekan pada buah",
                "C14": "Ada bercak berwarna kuning pada daun",
                "C15": "Ada cekungan berwarna gelap coklat kehitaman pada buah terutama ujung buah (pantat buah)"
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
                "D09": "Busuk Ujung Buah",
                "D10": "Pecah Buah"
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
                "S18": "Membalik tanah dan Membiarkan Terkena Sinar Matahari Selama Beberapa Hari",
                "S19": "Pemberian pupuk kalsium misalnya kapur atau dolomit"
            }
        }
        
        self.rules = [
            {
                "id": "R01", 
                "conditions": {"C01", "C02", "C07", "C09"}, 
                "disease": "D01", 
                "solutions": ["S01", "S02", "S03", "S04"],
            },
            {
                "id": "R02", 
                "conditions": {"C01", "C02", "C06", "C012"}, 
                "disease": "D02", 
                "solutions": ["S05", "S06", "S07"], 
            },
            {
                "id": "R03", 
                "conditions": {"C01", "C07", "C09"}, 
                "disease": "D03", 
                "solutions": ["S01", "S05", "S08", "S09"], 
            },
            {
                "id": "R04", 
                "conditions": {"C02", "C07", "C10", "C12"}, 
                "disease": "D04", 
                "solutions": ["S01", "S05", "S08", "S09"], 
            },
            {
                "id": "R05", 
                "conditions": {"C01", "C02", "C06", "C07", "C08"}, 
                "disease": "D05", 
                "solutions": ["S01", "S05", "S08", "S09"], 
            },
            {
                "id": "R06", 
                "conditions": {"C01", "C09", "C14"}, 
                "disease": "D06", 
                "solutions": ["S01", "S05", "S09", "S10"], 
            },
            {
                "id": "R07", 
                "conditions": {"C01", "C06", "C10", "C14"}, 
                "disease": "D07", 
                "solutions": ["S01", "S05", "S09", "S10"], 
            },
            {
                "id": "R08", 
                "conditions": {"C03", "C06", "C10", "C14"}, 
                "disease": "D08", 
                "solutions": ["S11", "S12", "S13", "S14"], 
            },
            {
                "id": "R09", 
                "conditions": {"C03","C15"}, 
                "disease": "D09", 
                "solutions": ["S07", "S14", "S17", "S19"], 
            },
            {
                "id": "R10", 
                "conditions": {"C13"}, 
                "disease": "D10", 
                "solutions": ["S14", "S16", "S15", "S18"], 
            }
            
        ]

    def forward_chain(self, input_conditions: Set[str]) -> List[Dict]:
        results = []
    
        for rule in self.rules:
            if rule["conditions"] == input_conditions:  
                disease_desc = self.mappings["diseases"][rule["disease"]]
                solution_desc = [self.mappings["solutions"][s] for s in rule["solutions"]]
                condition_desc = [self.mappings["conditions"][c] for c in rule["conditions"]]

                results.append({
                    "rule_id": rule["id"],
                    "disease": disease_desc,
                    "solutions": solution_desc,
                    "matched_conditions": condition_desc
                })
        if not results:
            return [{"message": "Tidak ada diagnosis yang cocok untuk kondisi yang diberikan."}]
    
        return results

    def get_mappings(self) -> Dict:
        return {
            "conditions": [
                {"id": key, "description": value}
                for key, value in self.mappings["conditions"].items()
            ],
            "diseases": [
                {"id": key, "description": value}
                for key, value in self.mappings["diseases"].items()
            ],
            "solutions": [
                {"id": key, "description": value}
                for key, value in self.mappings["solutions"].items()
            ]
    }

    def get_diagnosis_by_rule_id(self, rule_id: str) -> dict:
        for rule in self.rules:
            if rule["id"] == rule_id:
                disease_desc = self.mappings["diseases"][rule["disease"]]
                solution_desc = [self.mappings["solutions"][s] for s in rule["solutions"]]
                condition_desc = [self.mappings["conditions"][c] for c in rule["conditions"]]

                return {
                    "rule_id": rule["id"],
                    "disease": disease_desc,
                    "solutions": solution_desc,
                    "matched_conditions": condition_desc
                }
        return None


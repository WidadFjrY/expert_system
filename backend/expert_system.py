from typing import List, Dict, Set

class TomatoExpertSystem:
    def __init__(self):
        self.rules = [
            {
                "id": "", # Id rule, contoh R01
                "conditions": {}, # Kode kondisi, contoh {"C01", "C02", "C03"},
                "disease": "", # Kode penyakit, contoh "D01"
                "solutions": [], # Kode solusi, contoh ["S01", "S02", "S03"]
            },
        ]
        
        self.mappings = {
            "conditions": {
                # Variable kondisi, contoh  "C01": "Daun menguning dengan tepi coklat",
            },
            "diseases": {
                # Variable penyakit, contoh "D01": "Penyakit Hawar Daun (Early Blight)",
            },
            "solutions": {
                # Variable solusi, contoh "S01": "Gunakan fungisida mengandung chlorothalonil",
            }
        }

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
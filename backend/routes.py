from fastapi import FastAPI
from fastapi.responses import JSONResponse
from pydantic import BaseModel
from typing import List, Set
from expert_system import TomatoExpertSystem

app = FastAPI()
expert_system = TomatoExpertSystem()

class DiagnosisRequest(BaseModel):
    condition_codes: List[str]

class DiagnosisResponse(BaseModel):
    rule_id: str
    disease: str
    solutions: List[str]
    matched_conditions: List[str]

@app.post("/diagnose-rule")
def diagnose_rule(request: DiagnosisRequest):
    input_conditions = set(request.condition_codes)
    result = expert_system.forward_chain(input_conditions)

    if isinstance(result, list) and "message" in result[0]:
        return JSONResponse(content=result[0], status_code=404)

    return {"rule_id": result[0]["rule_id"]}

@app.get("/diagnose/{rule_id}", response_model=DiagnosisResponse)
def get_diagnosis(rule_id: str):
    result = expert_system.get_diagnosis_by_rule_id(rule_id)

    if result is None:
        return JSONResponse(content={"message": "Rule tidak ditemukan"}, status_code=404)

    return result

@app.get("/mappings")
def get_mappings():
    return expert_system.get_mappings()
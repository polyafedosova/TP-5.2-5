package medical

import dto.TreatmentDtoGet

interface TreatmentCallback {
    fun onDataReceived(data: List<TreatmentDtoGet>)
    fun onFailure(code: Int, message: String)
}
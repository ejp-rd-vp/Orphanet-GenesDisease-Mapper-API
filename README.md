# Orphanet-GenesDisease-Mapper-API
This API focuses on Diseases and  Genes related to them, Orphanet proposes a link between genes and diseases related to them ant this API implements that.

## Gene to disease(s)

### API parameters
'gendis/find?by={hgnc code or Symbol}&input={the value of hgnc code or Symbol}

ex: [http://purl.org/gendis/find?by=hgnc&input=3603]

ex: [http://purl.org/gendis/find?by=symbol&input=FBN1] 

You will obtain the list of diseases linked to the specific gene (by code or symbol)

Returns Orphacodes and label:

*"orphaCode":"http://www.orpha.net/ORDO/Orphanet_284963",
"label":"Marfan syndrome type 1"

*"inferredResourceResponses": In Orphanet Knowledge base, genes are linked to specific entities such as "subtype of disease". For instance, FBN1 gene will be linked to Marfan type 1 and FBN2 gene will be linked to Marfan type 2. However, resources are often annotated at the level of "disorder" clinical entities. (Marfan syndrome in this example)

The API will return a result expanded to this "upper" level (from subtype to disorder level) when relevant as "inferredResourceResponses" :

*"inferredResourceResponses":
{"orphaCode":"http://www.orpha.net/ORDO/Orphanet_558",
"label":"Marfan syndrome"}

*"orphanetEndpoint"
We added a feature that allows to query the Orphanet Resources Endpoint (Biobanks and Registries) based on the returned list of Orphacodes

export interface TourPackagePrice {
    tourId: string,
    operatorId: string,
    inclusiveFlag: boolean,
    
    g1S1: number,
    g1S2: number,
    g1S3: number,
    g1S4: number,
    g1S5: number,
        
    g2S1: number,
    g2S2: number,
    g2S3: number,
    g2S4: number,
    g2S5: number,
        
    g3S1: number,
    g3S2: number,
    g3S3: number,
    g3S4: number,
    g3S5: number,
        
    g4S1: number,
    g4S2: number,
    g4S3: number,
    g4S4: number,
    g4S5: number,
        
    g5S1: number,
    g5S2: number,
    g5S3: number,
    g5S4: number,
    g5S5: number,
}

export interface TourRoute {
    tourId: string,
    tourRoute: string,
    tourDescription: string,
}

export interface GroupSeasonCast {
    operatorId: string,
    
    season1StartDate: string,
    season2StartDate: string,
    season3StartDate: string,
    season4StartDate: string,
    season5StartDate: string,
    
    g1MinSize: number,
    g2MinSize: number,
    g3MinSize: number,
    g4MinSize: number,
    g5MinSize: number,
}

export interface Tour {
    tourId: string,
    tourRoute: string,
    tourDuration: number,
    tourMaxSize: number,
    tourMinSize: number,
    tourType: string,
    tourStartPlace: string,
    tourStartTime: string,
    tourStartDate: string,
    tourEndDate: string,
    tourPrice: number,
    operatorId: string,
}

export interface Car {
    carId: string,
    operatorId: string,
    operatorName: string,
    carType: string,
    carModel: string,
    passangerSize: number,
    doorCount: number,
    withDriver: boolean,
    dailyPrice: number,
    houseNumber: string,
    buildingName: string,
    kebela: string,
    county: string,
    subCity: string,
    city: string,
    returnAddress: boolean,
    automaticTransmission: boolean,
    carCount: number,

    return_houseNumber: string,
    return_buildingName: string,
    return_kebela: string,
    return_county: string,
    return_subCity: string,
    return_city: string,
}
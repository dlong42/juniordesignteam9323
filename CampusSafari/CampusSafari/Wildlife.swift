//
//  Wildlife.swift
//  CampusSafari
//
//  Created by Davis Williams on 11/8/19.
//  Copyright © 2019 Team 9323. All rights reserved.
//

import Foundation
import MapKit

class Wildlife: NSObject, MKAnnotation {
    let title: String?
    let locationName: String
    let level: String
    let coordinate: CLLocationCoordinate2D
    
    init(title: String, locationName: String, level: String, coordinate: CLLocationCoordinate2D) {
        self.title = title
        self.locationName = locationName
        self.level = level
        self.coordinate = coordinate
        
        super.init()
    }
    
    var subtitle: String? {
        return level
    }
}

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
    let level: Int
    let coordinate: CLLocationCoordinate2D
    let kingdom: String
    
    init(title: String, locationName: String, level: Int, kingdom: String, coordinate: CLLocationCoordinate2D) {
        self.title = title
        self.locationName = locationName
        self.level = level
        self.coordinate = coordinate
        self.kingdom = kingdom
        
        super.init()
    }
    
    var subtitle: String? {
        return "Level \(level)"
    }
    
    var markerTintColor: UIColor {
        if (level > 3) {
            return .gray
        } else {
            return .green
        }
    }
}

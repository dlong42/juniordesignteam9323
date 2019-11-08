//
//  ViewController.swift
//  CampusSafari
//
//  Created by Davis Williams on 11/4/19.
//  Copyright © 2019 Team 9323. All rights reserved.
//

import UIKit
import MapKit

class ViewController: UIViewController {

    let regionRadius: CLLocationDistance = 1000
    let coordBoundary = MKCoordinateRegion(center: CLLocationCoordinate2D(latitude: 33.7765, longitude: -84.3983), latitudinalMeters: 939, longitudinalMeters: 1715)
    
    @IBOutlet weak var mapView: MKMapView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        let initialLocation = CLLocation(latitude: 33.7765, longitude: -84.3983)
        centerMapOnLocation(location: initialLocation)
        mapView.setCameraBoundary(MKMapView.CameraBoundary(coordinateRegion: coordBoundary), animated: true)
    }
    
    func centerMapOnLocation(location: CLLocation) {
        let coordinateRegion = MKCoordinateRegion(center: location.coordinate, latitudinalMeters: regionRadius, longitudinalMeters: regionRadius)
        mapView.setRegion(coordinateRegion, animated: true)
    }
}


import { Component, AfterViewInit, OnInit } from '@angular/core';
import { SparqlService } from 'src/app/services/sparql.service';

@Component({
  selector: 'app-sparql',
  templateUrl: './sparql.component.html',
  styleUrls: ['./sparql.component.css']
})
export class SparqlComponent {

  public statement = null;
  public result = null;

  constructor(
    private sparqlService: SparqlService
  ) {}

  public executeStatement () {
    let self = this;
    this.sparqlService.executeStatement(this.statement).subscribe(response => {
      self.result = response;
    });
  }
}

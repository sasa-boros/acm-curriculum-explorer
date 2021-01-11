import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { SparqlComponent } from './components/sparql/sparql.component';
import { MetahandRoutingModule } from './metahand-routing.module';



@NgModule({
  declarations: [SparqlComponent],
  imports: [
    CommonModule,
    MetahandRoutingModule,
    FormsModule,
    MatInputModule,
    MatFormFieldModule,
    MatButtonModule
  ],
  exports: []
})
export class MetahandModule { }
